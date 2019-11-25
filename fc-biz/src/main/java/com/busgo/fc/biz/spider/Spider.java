package com.busgo.fc.biz.spider;

import com.busgo.fc.inner.model.House;

import java.util.List;

/**
 * @author busgo
 * @date 2019-11-16 18:15
 */
public interface Spider {


    /**
     * 抓取内容
     *
     * @return
     */
    String crawl();


    /**
     * 入库
     *
     * @param houseList 房屋列表
     */
    void store(List<House> houseList);
}
