package com.busgo.fc.biz.component;

import com.busgo.fc.biz.spider.Spider;
import com.busgo.fc.commons.constants.Source;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author busgo
 * @date 2019-11-18 15:14
 */
@Component
public class SpiderComponent implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    private ConcurrentHashMap<Integer, Spider> spiders = new ConcurrentHashMap<>();

    /**
     * 爬取
     *
     * @param source 来源
     * @return
     */
    public String crawl(Integer source) {

        Spider spider = this.getSpider(source);

        if (spider == null) return null;


        return spider.crawl();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;

    }


    private Spider getSpider(Integer source) {

        if (CollectionUtils.isEmpty(spiders)) {
            this.spiders.put(Source.W, this.applicationContext.getBean("WSpider", Spider.class));
            this.spiders.put(Source.L, this.applicationContext.getBean("LSpider", Spider.class));
        }

        return this.spiders.get(source);
    }


}
