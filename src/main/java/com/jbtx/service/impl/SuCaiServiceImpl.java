package com.jbtx.service.impl;

import com.jbtx.dao.SucaiMapper;
import com.jbtx.entity.Sucai;
import com.jbtx.service.SuCaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class SuCaiServiceImpl implements SuCaiService {
    @Autowired
    private SucaiMapper sucaiMapper;

    @Override
    public Long addSuCai(String name, String address, String type) {
        Sucai sucai = new Sucai();
        sucai.setSucainame(name);
        sucai.setAddress(address);
        sucai.setCreateid(1L);
        sucai.setCreatetime(new Date());
        sucai.setIsdelete("1");
        sucai.setType(type);
        sucaiMapper.insertSelective(sucai);
        return sucai.getId();
    }
}
