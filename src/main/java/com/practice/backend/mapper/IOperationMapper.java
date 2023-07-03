package com.practice.backend.mapper;


import com.practice.backend.model.operation.Operation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOperationMapper extends IMapper<Operation> {
}
