package com.practice.backend.dao.service;

import com.practice.backend.mapper.IOperationMapper;
import com.practice.backend.dao.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationService extends AbstractService<IOperationMapper, Operation> {

    @Autowired
    public OperationService(IOperationMapper mapper) {
        this.mapper = mapper;
    }
}
