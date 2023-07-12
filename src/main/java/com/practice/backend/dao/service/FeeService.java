package com.practice.backend.dao.service;

import com.practice.backend.dao.model.Fee;
import com.practice.backend.dao.mapper.IFeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService extends AbstractServiceWithSectorId<IFeeMapper, Fee> {

    @Autowired
    public FeeService(IFeeMapper mapper) {
        this.mapper = mapper;
    }
}
