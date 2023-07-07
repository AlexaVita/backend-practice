package com.practice.backend.mapper;

import com.practice.backend.dao.model.Example;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExampleMapper {
    //@Select("select * from example")
    List<Example> getAll();

}

