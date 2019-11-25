package com.busgo.fc.biz.spider.l;

import com.busgo.fc.biz.spider.AbstractSpider;
import com.busgo.fc.commons.constants.Source;
import com.busgo.fc.commons.util.DateUtils;
import com.busgo.fc.commons.util.HttpClientUtils;
import com.busgo.fc.inner.model.House;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 链家爬取二手房信息
 *
 * @author busgo
 * @date 2019-11-19 09:55
 */
@Component
public class LSpider extends AbstractSpider {


    private final static Logger log = LoggerFactory.getLogger(LSpider.class);


    private static Set<String> sqs = new HashSet<>();


    private ThreadPoolExecutor exec = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2 + 1, Runtime.getRuntime().availableProcessors() * 2 + 1, 5L, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(500), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }
    });


    static {


        sqs.add("zhuantang");
        sqs.add("jiulian");
        sqs.add("gudang");
        sqs.add("wensanxilu");
        sqs.add("wenyixilu");
        sqs.add("jialv");
        sqs.add("zhijiang");
        sqs.add("liuxia1");
        sqs.add("cuiyuan");
        sqs.add("shenhua");
        sqs.add("wenjiao");
        sqs.add("wensan");
        sqs.add("xihujingqu");
        sqs.add("sandun");
        sqs.add("xixi");
        sqs.add("xuejun");
        sqs.add("xiaoheshan");
        sqs.add("dajiangdong1");
        sqs.add("gongyeyuanbei");
        sqs.add("yanjiangbei");
        sqs.add("dajiangdong");
        sqs.add("yanjiangnan");
        sqs.add("gongyeyuannan");
        sqs.add("gaojiaoyuanquxi");
        sqs.add("haining");
        sqs.add("daxuechengbei");
        sqs.add("gaojiaoyuanqudong");
        sqs.add("jinshahu");
        sqs.add("deshengdong");
        sqs.add("tiyuchanglu");
        sqs.add("chaohui");
        sqs.add("zhonganqiao");
        sqs.add("changqing1112");
        sqs.add("huanglong");
        sqs.add("tianshui1");
        sqs.add("sichoucheng1");
        sqs.add("wulin11");
        sqs.add("heping2");
        sqs.add("jianqiao");
        sqs.add("jingfang1");
        sqs.add("sanliting");
        sqs.add("nanxiaobu");
        sqs.add("zhanongkou");
        sqs.add("huochedongzhan");
        sqs.add("liushuiyuan");
        sqs.add("chengdongxincheng");
        sqs.add("jiubao");
        sqs.add("shiqiao");
        sqs.add("jianguobeilu");
        sqs.add("qianjiangxincheng");
        sqs.add("caihe1");
        sqs.add("huajiachi");
        sqs.add("hushu1");
        sqs.add("santang");
        sqs.add("wandaguangchang2");
        sqs.add("qiaoxi1");
        sqs.add("gouzhuang");
        sqs.add("daguan");
        sqs.add("desheng");
        sqs.add("hemu");
        sqs.add("gongchenqiao");
        sqs.add("dingqiao");
        sqs.add("xinyifang");
        sqs.add("jinjiang1");
        sqs.add("qingbo");
        sqs.add("qingtai");
        sqs.add("hubin1");
        sqs.add("wangjiang");
        sqs.add("chengzhan");
        sqs.add("gulou2");
        sqs.add("xiongzhenlou");
        sqs.add("chaoming11");
        sqs.add("nanxing");
        sqs.add("fuxing");
        sqs.add("sijiqing1");
        sqs.add("caihongcheng");
        sqs.add("puyan");
        sqs.add("baimahu");
        sqs.add("changhe");
        sqs.add("aoti");
        sqs.add("xixing");
        sqs.add("binjiangquzhengfu");
        sqs.add("xingqiao");
        sqs.add("tangqi1");
        sqs.add("feicuicheng1");
        sqs.add("weilaikejicheng");
        sqs.add("pingyao");
        sqs.add("renhe2");
        sqs.add("linping");
        sqs.add("linan1");
        sqs.add("qiaosi");
        sqs.add("liangzhu");
        sqs.add("chongxian");
        sqs.add("xianlin1");
        sqs.add("banshan");
        sqs.add("yiqiao");
        sqs.add("xiaoshanshiqu");
        sqs.add("linpu");
        sqs.add("wenyan");
        sqs.add("xianghu");
        sqs.add("xiaoshankaifaqu");
        sqs.add("nanbuwocheng");
        sqs.add("xiaoshanxinchengqu");
        sqs.add("qianjiangshijicheng");
        sqs.add("tonglushangquan");
        sqs.add("chunan");
        sqs.add("jiande1");
        sqs.add("fuyang1");
        sqs.add("linan");


    }

    @Override
    public String crawl() {


        for (String sq : sqs) {


            this.handleSq(sq);

        }

        return "链家数据以及爬取完毕";


    }


    private void handleSq(final String sq) {


        int totalPage = 100;
        int pageSize = 30;


        int pageNo = 1;

        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://hz.lianjia.com/ershoufang/rs/");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");

        while (pageNo <= totalPage) {


            String url = String.format("https://hz.lianjia.com/ershoufang/%s/pg%d/", sq, pageNo);
            String content = HttpClientUtils.doGet(url, null, headers);
            Document document = Jsoup.parse(content);

            final Elements elements = document.getElementsByClass("item");
            if (elements.size() == 0) continue;


            Elements totalElements = document.getElementsByClass("total fl");

            Element element = totalElements.get(0);

            Element child = element.child(0);
            String totalStr = child.text();


            if (pageNo == 1 || totalPage == 100 || totalPage == 0) {

                int total = Integer.parseInt(totalStr.trim());

                totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
                log.info(Thread.currentThread().getName() + "解析总页数:{},当前页:{},sq:{}", totalPage, pageNo, sq);
            }


            this.exec.submit(new Runnable() {
                @Override
                public void run() {

                    List<House> houseList = new ArrayList<>();
                    for (Element item : elements) {


                        String houseId = item.attr("data-houseid");

                        if (StringUtils.isBlank(houseId)) continue;

                        String itemDetailUrl = String.format("https://hz.lianjia.com/ershoufang/%s.html", houseId);

                        try {
                            log.info(Thread.currentThread().getName() + " start parse:{},sq:{}", itemDetailUrl, sq);
                            House house = parseItem(itemDetailUrl);
                            if (house != null) {
                                house.setSource(Source.L);
                                house.setBizId(Long.parseLong(houseId));
                                int time = DateUtils.getDateAsIntYYYYMMdd(new Date());
                                String format = String.format("%s000000000", time);
                                house.setUpdateTime(Long.valueOf(format));
                                house.setUpDown(1);
                                houseList.add(house);
                            }
                        } catch (Exception e) {

                            log.error("解析链家房屋信息出现异常:{},sq:{}", itemDetailUrl, sq, e);
                        }

                    }

                    // 存储
                    store(houseList);
                }
            });

            if (pageNo > 100 || pageNo > totalPage) {
                log.warn(Thread.currentThread().getName() + "解析总页数:{},当前页:{},已经超过最大页数了sq:{}", totalPage, pageNo, sq);

                break;
            }
            pageNo++;


        }
    }

    private House parseItem(String detailUrl) {


        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://hz.lianjia.com/ershoufang/rs/");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");
        String content = HttpClientUtils.doGet(detailUrl, null, headers);

        Document document = Jsoup.parse(content);


        House house = new House();

        // 解析标题
        this.parseTitle(document, house);

        // 解析区域
        this.parseQy(document, house);


        // 解析价格
        this.parsePrice(document, house);

        // 解析建筑年代
        this.parseHouseInfo(document, house);


        // 解析房屋说明
        this.parseIntroContent(document, house);

        // 解析小区名称
        this.parseCommunityName(document, house);
        return house;

    }

    private void parseCommunityName(Document document, House house) {

        Element element = document.select("div[class=communityName] a").get(0);

        String communityName = element.text();

        house.setCommunityName(communityName);

    }


    /**
     * 解析房屋说明
     *
     * @param document 文档
     * @param house    房屋
     */
    private void parseIntroContent(Document document, House house) {

        Elements elements = document.getElementsByClass("introContent").get(0).children().select("div[class=base] div[class=content] ul li");

        String text = elements.get(0).text().replaceAll("房屋户型", "").replaceAll("别墅类型", "");

        int index = text.indexOf("室");
        String room = text.substring(0, index);

        text = text.substring(index + 1, text.length());

        index = text.indexOf("厅");
        String live = text.substring(0, index);


        text = text.substring(index + 1, text.length());


        index = text.indexOf("厨");

        text = text.substring(index + 1, text.length());


        index = text.indexOf("卫");
        String toilet = text.substring(0, index);
        house.setBedRoom(Integer.parseInt(room));
        house.setLivingRoom(Integer.parseInt(live));
        house.setToilet(Integer.parseInt(toilet));

        // 解析面积
        String area = elements.get(2).text().replaceAll("建筑面积", "").replaceAll("㎡", "");


        house.setArea(new BigDecimal(area));

        // 房屋朝向
        String heading = elements.get(6).text().replaceAll("房屋朝向", "").replaceAll(" ", "");

        house.setHeading(heading);
        // 装修情况
        String decorateLevel = elements.get(8).text().replaceAll("装修情况", "");
        house.setDecorateLevel(decorateLevel);


        elements = document.getElementsByClass("introContent").get(0).children().select("div[class=transaction] div[class=content] ul li");

        String publishTime = elements.get(0).children().get(1).text();
        house.setPublicTime(DateUtils.parseDate(publishTime.trim(), DateUtils.yyyy_MM_dd));


        // 房屋类型
        String houseType = elements.get(3).children().get(1).text();
        house.setHouseType(houseType.trim());


        if (elements.size() > 8 && elements.get(8).children().size() > 0) {
            // 核验码
            String governmentCode = elements.get(8).children().get(1).text();
            house.setGovernmentCode(governmentCode.trim());
        }


    }


    /**
     * 解析建筑信息
     *
     * @param document 文档
     * @param house    房屋
     */
    private void parseHouseInfo(Document document, House house) {


        Element element = document.select("div[class=houseInfo]").get(0).children().select("div[class=room]").get(0).children().select("div[class=subInfo]").get(0);


        String text = element.text();

        String allFloor = "";
        if (text.indexOf("/") > 0) {
            String[] split = text.split("/");
            allFloor = split[1].replaceAll("共", "").replaceAll("层", "");
        } else {
            allFloor = text.replaceAll("共", "").replaceAll("层", "");
        }


        house.setHouseAllFloor(Integer.parseInt(allFloor.trim()));


        element = document.select("div[class=houseInfo]").get(0).children().select("div[class=area]").get(0).children().select("div[class=subInfo]").get(0);

        String year = element.text().split("年")[0];


        if (year.equals("未知")) return;

        house.setBuildYear(Integer.parseInt(year));


    }


    /**
     * 解析价格
     *
     * @param document 文档
     * @param house    房屋
     */
    private void parsePrice(Document document, House house) {

        Elements elements = document.select("div[class=price]");

        Element element = elements.get(0).child(0);

        String price = element.text();

        Elements select = elements.get(0).children().select("div[class=text]");

        Element ele = select.get(0);
        String unitPrice = ele.children().get(0).child(0).text().replaceAll("元/平米", "");
        house.setPrice(new BigDecimal(price));
        house.setUnitPrice(Integer.parseInt(unitPrice));
    }


    /**
     * 解析区域
     *
     * @param document 文档
     * @param house    房屋
     */
    private void parseQy(Document document, House house) {

        Elements elements = document.getElementsByClass("fl l-txt");

        if (elements.size() == 0) return;

        Element element = elements.get(0);

        Elements tags = element.getElementsByTag("a");

        if (tags.size() == 0) return;

        Element qyElement = tags.get(2);
        Element sqElement = tags.get(3);

        String qyHref = qyElement.attr("href");
        String qySell = qyHref.split("/")[2] + "qu";
        String qySellName = qyElement.text().replaceAll("二手房", "");
        String sqHref = sqElement.attr("href");
        String sqSell = sqHref.split("/")[2];
        String sqName = sqElement.text().replaceAll("二手房", "");

        house.setQySpell(qySell);
        house.setQyName(qySellName);
        house.setSqName(sqName);
        house.setSqSpell(sqSell);
    }

    /**
     * 解析标题
     *
     * @param document 文档
     * @return
     */
    private void parseTitle(Document document, House house) {
        Elements mainElement = document.getElementsByClass("main");

        house.setTitle(mainElement.text());
    }


    public static void main(String[] args) {

        LSpider lSpider = new LSpider();
        lSpider.crawl();
    }
}
