package com.hyh.service;

import com.maxwisdom.parallel.dao.CityPartenerDao;
import com.maxwisdom.parallel.entity.CityPartener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityPartenerServiceImpl implements CityPartenerService {

    @Autowired
    private CityPartenerDao cityPartenerDao;


    @Override
    public void save(CityPartener cityPartener) {

        try {
            if (cityPartener != null){
                cityPartenerDao.save(cityPartener);
                System.out.println("添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
