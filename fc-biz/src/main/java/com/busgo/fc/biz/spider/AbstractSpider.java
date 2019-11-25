package com.busgo.fc.biz.spider;

import com.busgo.fc.biz.component.HouseComponent;
import com.busgo.fc.inner.dao.HouseDao;
import com.busgo.fc.inner.model.House;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author busgo
 * @date 2019-11-19 15:36
 */
public abstract class AbstractSpider implements Spider {


    private final static Logger log = LoggerFactory.getLogger(AbstractSpider.class);


    @Resource
    private HouseComponent houseComponent;

    @Autowired
    private HouseDao houseDao;


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
}
