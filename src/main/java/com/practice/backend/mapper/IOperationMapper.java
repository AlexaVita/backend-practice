package com.practice.backend.mapper;


import com.practice.backend.model.Operation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOperationMapper extends IMapper<Operation> {
}