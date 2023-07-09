package com.practice.backend.dao.mapper;

import com.practice.backend.dao.model.Sector;
import org.apache.ibatis.annotations.Mapper;

/** Все запросы объявлены в IMapper, а реализации их: в ISectorMapper.xml */
@Mapper
public interface ISectorMapper extends IMapper<Sector> {

}
