package com.busgo.fc.web.api;

import com.busgo.fc.biz.component.SpiderComponent;
import com.busgo.fc.commons.constants.Source;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author busgo
 * @date 2019-11-18 15:13
 */
@RestController
@RequestMapping("/crawl")
public class CrawlApi {


    @Resource
    private SpiderComponent spiderComponent;


    @GetMapping("")
    public String crawl(Integer type) {

        Assert.notNull(type, "房产中介类型不能为空");

        Assert.isTrue(type == Source.W || type == Source.L, "非法的房产中介类型");

        return this.spiderComponent.crawl(type);
    }
}
