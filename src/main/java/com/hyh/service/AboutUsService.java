package com.hyh.service;

import com.maxwisdom.parallel.entity.AboutUs;

public interface AboutUsService {
    String findByshowAboutUs(AboutUs aboutUs);

    String aboutUsSelect(AboutUs aboutUs);

    public String updateShowAbuotUs(AboutUs aboutUs) ;

    String aboutUs_delete(Integer[] idArray);

    public AboutUs findById(Long id);
}
