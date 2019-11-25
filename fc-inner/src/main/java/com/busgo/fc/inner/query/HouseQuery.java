package com.busgo.fc.inner.query;

import    lombok.Data;
import    lombok.ToString;

import    java.util.Date;
import    java.math.BigDecimal;
import    java.util.List;
/***
 *
 * @author Create By AutoGenerator
 */
@Data
@ToString(callSuper = true)
public class HouseQuery extends BaseQuery {


	// 主键
	private Long id;

	// 业务房子id
	private Long bizId;

	// 标题
	private String title;

	// 来源  1-我爱我家  2-链家
	private Integer source;

	// 价格
	private BigDecimal price;

	// 图片地址
	private String imgurl;

	// 上下架 1-上架
	private Integer upDown;

	// 面积
	private BigDecimal area;

	// 房屋类型
	private String houseType;

	// 单价
	private Integer unitPrice;

	// 小区名
	private String communityName;

	// 定位
	private String locationX;

	// 
	private String locationY;

	// 几室
	private Integer bedRoom;

	// 几厅
	private Integer livingRoom;

	// 卫
	private Integer toilet;

	// 商圈名称
	private String sqName;

	// 商圈拼音
	private String sqSpell;

	// 区域名称
	private String qyName;

	// 区域拼音
	private String qySpell;

	// 更新时间
	private Long updateTime;

	// 下架时间
	private Long downTime;

	// 朝向
	private String heading;

	// 总楼层
	private Integer houseAllFloor;

	// 所属层
	private Integer buildingFloor;

	// 装修类型
	private String decorateLevel;

	// 建筑年代
	private Integer buildYear;

	// 发布时间
	private Date publicTime;

	// 核验编码
	private String governmentCode;

	// 创建时间
	private Date createTime;

	// 修改时间
	private Date modifyTime;

    private List<Long> includeIdList;

    private List<Long> excludeIdList;

}
