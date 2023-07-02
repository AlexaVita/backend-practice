package com.practice.backend.mapper;

import com.practice.backend.model.Sector;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/** Все запросы объявлены в IMapper, а реализации их: в ISectorMapper.xml */
@Mapper
public interface ISectorMapper extends IMapper<Sector> {

}
