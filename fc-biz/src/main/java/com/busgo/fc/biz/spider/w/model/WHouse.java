package com.busgo.fc.biz.spider.w.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author busgo
 * @date 2019-11-18 10:07
 */
@Data
public class WHouse implements Serializable {


    // 房子id
    private Long housesid;
    // 标题
    private String housetitle;

    private List<Double> location;

    // 价格
    private BigDecimal price;

    // 图片地址
    private String imgurl;

    // 面积
    private BigDecimal buildarea;

    // 楼层
    private String floorPositionStr;

    // 上下架状态
    private Integer updown;


    // 单价
    private Integer unitprice;

    // 社区名
    private String communityname;

    private String heading;

    //几室
    private Integer bedroom;

    // 厅
    private Integer livingroom;

    // 卫
    private Integer toilet;


    private Integer buildage;


    // 建筑年代
    private Integer buildyear;


    // 楼层
    private Integer houseallfloor;


    // 当前层
    private Integer buildingfloor;

    //房屋种类
    private String housetype;

    // 装修级别
    private String decoratelevel;


    // 区域名称
    private String qyname;

    // 区域
    private String qyspell;


    // 商圈
    private String sqspell;

    // 商圈名称
    private String sqname;


    private String government_code;


    // 发布时间
    private String firstuptimestr;

    // 最近更新时间
    private Long uptime;

    // 下架时间
    private Long downtime;


}
