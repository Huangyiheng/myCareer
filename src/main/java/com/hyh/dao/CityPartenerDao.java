package com.hyh.dao;

import com.maxwisdom.parallel.entity.CityPartener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CityPartenerDao extends JpaRepository<CityPartener, Long>, JpaSpecificationExecutor<CityPartener> {

}
