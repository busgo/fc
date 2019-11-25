package com.busgo.fc.biz.spider.w;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.busgo.fc.biz.component.HouseComponent;
import com.busgo.fc.biz.spider.AbstractSpider;
import com.busgo.fc.biz.spider.Spider;
import com.busgo.fc.biz.spider.w.model.WHouse;
import com.busgo.fc.commons.constants.Source;
import com.busgo.fc.commons.util.DateUtils;
import com.busgo.fc.commons.util.HttpClientUtils;
import com.busgo.fc.inner.dao.HouseDao;
import com.busgo.fc.inner.model.House;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author busgo
 * @date 2019-11-16 18:15
 */
@Component
public class WSpider extends AbstractSpider {


    private final static Logger log = LoggerFactory.getLogger(WSpider.class);


    @Resource
    private HouseComponent houseComponent;

    @Autowired
    private HouseDao houseDao;


    @Override
    public String crawl() {


        int pageNo = 1;


        Map<String, String> headers = new HashMap<>();


        headers.put("Referer", "https://m.5i5j.com/hz/ershoufang/");
        headers.put("x-requested-with", "XMLHttpRequest");
        headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
        String cookie = this.getCookie();
        headers.put("Cookie", cookie);


        while (true) {
            String url = String.format("https://m.5i5j.com/hz/ershoufang/n%d", pageNo);
            String content = HttpClientUtils.doGet(url, null, headers);
            if (StringUtils.isBlank(content) || StringUtils.equals("[]", content)) break;

            List<House> houseList = this.parse(content);

            this.store(houseList);

            pageNo++;


        }

        return String.format("一共爬取{}页我爱我家数据", pageNo);

    }

    @Override
    public void store(List<House> houseList) {


        if (CollectionUtils.isEmpty(houseList)) return;


        for (House house : houseList) {


            try {


                House h = this.houseComponent.findHouseByBizIdAndSource(house.getBizId(), house.getSource());
                if (h == null) {

                    house.setCreateTime(new Date());
                    this.houseDao.insert(house);
                    log.info("房屋信息:{},插入成功", house);

                } else {

                    if (h.getUpdateTime() != null && h.getUpdateTime().equals(house.getUpdateTime())) {
                        log.warn("房屋信息:{},未变化", house);
                        continue;
                    }
                    house.setId(h.getId());
                    house.setModifyTime(new Date());
                    house.setSource(null);
                    house.setBizId(null);
                    this.houseDao.updateById(house);
                    log.info("房屋信息:{},更新成功", house);
                }
            } catch (Exception e) {

                log.error("房屋信息:{},处理失败", house, e);

            }
        }


    }


    /**
     * 解析内容
     *
     * @param content 内容
     * @return
     */
    private List<House> parse(String content) {


        JSONObject jsonObject = JSON.parseObject(content);
        List<House> houseList = new ArrayList<>();
        if (jsonObject.containsKey("houses")) {

            String houses = jsonObject.getString("houses");

            System.err.println("houses=" + houses);

            JSONArray jsonArray = JSONArray.parseArray(houses);

            for (int i = 0; i < jsonArray.size(); i++) {

                WHouse wHouse = jsonArray.getJSONObject(i).getObject("_source", WHouse.class);

                System.err.println("wawjHouse:" + wHouse);

                House house = convertHouse(wHouse);
                if (house != null) {

                    houseList.add(house);
                }
            }


        }

        return houseList;

    }

    /**
     * 实体转换
     *
     * @param wHouse 实体
     * @return
     */
    private House convertHouse(WHouse wHouse) {
        if (wHouse == null) return null;


        House house = new House();
        house.setSource(Source.W);
        house.setArea(wHouse.getBuildarea());
        house.setBedRoom(wHouse.getBedroom());
        house.setBizId(wHouse.getHousesid());
        house.setCommunityName(wHouse.getCommunityname());
        house.setDownTime(wHouse.getDowntime());
        house.setGovernmentCode(wHouse.getGovernment_code());
        String imgurl = wHouse.getImgurl();
        if (StringUtils.isNotBlank(imgurl) && imgurl.indexOf("?") > 0) {
            imgurl = imgurl.substring(0, imgurl.indexOf("?"));
        }
        house.setImgurl(imgurl);
        house.setLivingRoom(wHouse.getLivingroom());
        house.setLocationX(wHouse.getLocation().get(0).toString());
        house.setLocationY(wHouse.getLocation().get(1).toString());
        house.setPrice(wHouse.getPrice());
        house.setSqName(wHouse.getSqname());
        house.setQyName(wHouse.getQyname());
        house.setQySpell(wHouse.getQyspell());
        house.setSqSpell(wHouse.getSqspell());
        house.setTitle(wHouse.getHousetitle());
        house.setUnitPrice(wHouse.getUnitprice());
        house.setUpDown(wHouse.getUpdown());
        house.setUpdateTime(wHouse.getUptime());
        house.setHouseType(wHouse.getHousetype());
        house.setHeading(wHouse.getHeading());
        house.setHouseAllFloor(wHouse.getHouseallfloor());
        house.setBuildingFloor(wHouse.getBuildingfloor());
        house.setBuildYear(wHouse.getBuildyear());
        house.setToilet(wHouse.getToilet());
        house.setDecorateLevel(wHouse.getDecoratelevel());
        house.setPublicTime(DateUtils.parseDate(wHouse.getFirstuptimestr(), DateUtils.DEFAULT_FORMAT));

        return house;
    }


    private String getCookie() {


        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://m.5i5j.com/hz/ershoufang/");
        headers.put("x-requested-with", "XMLHttpRequest");
        String url = "https://m.5i5j.com/hz/ershoufang/";
        String content = HttpClientUtils.getWAWJCookie(url, null, headers);
        System.err.println("cookie=" + content);
        return content;


    }

    public static void main(String[] args) throws IOException {

//
////        WSpider spider = new WSpider();
////        String url = String.format("https://m.5i5j.com/hz/ershoufang/n%d", 1);
////        String content = spider.crawl(url);
//
//        List<House> houseList = spider.parse(content);
//
//        System.err.println(houseList);
    }


}
