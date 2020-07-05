package com.hyh.service;

import com.maxwisdom.parallel.entity.SalesCalls;
import org.springframework.web.multipart.MultipartFile;

public interface SalesCallsService {
    public String getsalescallsDate(SalesCalls salesCalls);
    public String salescallsAdd(SalesCalls salesCalls);
    public String salescallsdelete(Integer[] IDArray);
    public String allocation(Integer[] IDArray, Long user);
    public String importexcl(MultipartFile importexcl);
}
