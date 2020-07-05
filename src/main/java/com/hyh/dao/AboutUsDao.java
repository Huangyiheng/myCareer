package com.hyh.dao;

import com.maxwisdom.parallel.entity.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AboutUsDao extends JpaRepository<AboutUs, Long>, JpaSpecificationExecutor<AboutUs> {
}
