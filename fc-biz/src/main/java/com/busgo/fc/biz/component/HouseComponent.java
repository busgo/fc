package com.busgo.fc.biz.component;

import com.busgo.fc.inner.dao.HouseDao;
import com.busgo.fc.inner.model.House;
import com.busgo.fc.inner.query.HouseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author busgo
 * @date 2019-11-18 15:28
 */
@Component
public class HouseComponent {

    @Autowired
    private HouseDao houseDao;

    /**
     * 根据业务房屋id、来源查询详情
     *
     * @param bizId  业务id
     * @param source 来源
     * @return
     */
    public House findHouseByBizIdAndSource(Long bizId, Integer source) {

        HouseQuery query = new HouseQuery();
        query.setBizId(bizId);
        query.setSource(source);
        query.setOffset(0);
        query.setRows(1);

        List<House> houseList = this.houseDao.queryListByParam(query);

        if (CollectionUtils.isEmpty(houseList)) return null;

        return houseList.get(0);
    }
}
