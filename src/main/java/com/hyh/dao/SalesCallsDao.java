package com.hyh.dao;

import com.maxwisdom.parallel.entity.SalesCalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SalesCallsDao extends JpaRepository<SalesCalls, Long>, JpaSpecificationExecutor<SalesCalls> {
public SalesCalls findBysalesCallsid(Long salesCallsid);
}
