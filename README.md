# fc

> 爬取某两个知名房产中介  杭州二手房挂牌数据


####  执行sql脚本

```sql


CREATE database house CHARACTER set utf8;

DROP TABLE IF EXISTS `house`;
CREATE TABLE `house` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `biz_id` bigint(20) NOT NULL COMMENT '业务房子id',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `source` tinyint(4) NOT NULL COMMENT '来源  1-我爱我家  2-链家',
  `price` decimal(12,3) NOT NULL COMMENT '价格',
  `imgurl` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `up_down` tinyint(4) NOT NULL COMMENT '上下架 1-上架',
  `area` decimal(6,2) NOT NULL COMMENT '面积',
  `house_type` varchar(32) DEFAULT NULL COMMENT '房屋类型',
  `unit_price` int(11) NOT NULL COMMENT '单价',
  `community_name` varchar(32) NOT NULL COMMENT '小区名',
  `location_x` varchar(32) DEFAULT NULL COMMENT '定位',
  `location_y` varchar(255) DEFAULT NULL,
  `bed_room` int(11) NOT NULL COMMENT '几室',
  `living_room` int(11) NOT NULL COMMENT '几厅',
  `toilet` tinyint(4) DEFAULT NULL COMMENT '卫',
  `sq_name` varchar(32) NOT NULL COMMENT '商圈名称',
  `sq_spell` varchar(255) NOT NULL COMMENT '商圈拼音',
  `qy_name` varchar(32) NOT NULL COMMENT '区域名称',
  `qy_spell` varchar(32) NOT NULL COMMENT '区域拼音',
  `update_time` bigint(20) NOT NULL COMMENT '更新时间',
  `down_time` bigint(20) DEFAULT NULL COMMENT '下架时间',
  `heading` varchar(255) DEFAULT NULL COMMENT '朝向',
  `house_all_floor` int(11) NOT NULL COMMENT '总楼层',
  `building_floor` tinyint(4) DEFAULT NULL COMMENT '所属层',
  `decorate_level` varchar(32) DEFAULT NULL COMMENT '装修类型',
  `build_year` int(11) DEFAULT NULL COMMENT '建筑年代',
  `public_time` datetime NOT NULL COMMENT '发布时间',
  `government_code` varchar(255) DEFAULT NULL COMMENT '核验编码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_biz_id_souce` (`biz_id`,`source`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT '房屋信息表';

```

####  修改配置

##### 修改数据库配置信息

```java

spring.datasource.druid.name=dataSource
spring.datasource.druid.url=jdbc:mysql://127.0.0.1:3306/house?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.druid.username=root
spring.datasource.druid.password=123456
spring.datasource.druid.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.druid.initialSize=5
spring.datasource.druid.minIdle=5
spring.datasource.druid.maxActive=20
spring.datasource.druid.maxWait=60000
spring.datasource.druid.timeBetweenEvictionRunsMillis=60000
spring.datasource.druid.minEvictableIdleTimeMillis=300000
spring.datasource.druid.validationQuery=SELECT 1 FROM DUAL
spring.datasource.druid.testWhileIdle=true
spring.datasource.druid.testOnBorrow=false
spring.datasource.druid.testOnReturn=false
spring.datasource.druid.poolPreparedStatements=true
spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize=20
spring.datasource.druid.filters=stat,wall
spring.datasource.druid.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

```

####  快速启动

```shell

  # maven编译打包
  mvn clean package
  
  ## 启动
  nohup java -jar  fc-web/target/house.jar  >> /dev/null 2>&1 &  

```



####  开始抓取

> 我爱我家 >>> curl 
