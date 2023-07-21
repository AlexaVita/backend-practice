package com.practice.backend.dao.mapper;

import com.practice.backend.dao.model.Fee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IFeeMapper extends IMapperWithSectorId<Fee>{

    // TODO: повесить уточнения типов для enum, чтобы батис точно понимал, как парсить строку в перечисления

}
