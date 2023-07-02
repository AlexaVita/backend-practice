package com.practice.backend.service;

import com.practice.backend.mapper.IFeeMapper;
import com.practice.backend.model.Fee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService extends AbstractService<IFeeMapper, Fee> {

    @Autowired
    public FeeService(IFeeMapper mapper) {
        this.mapper = mapper;
    }
}
