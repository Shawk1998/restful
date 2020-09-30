package com.test.restful.Dao;

import com.test.restful.domain.ClassInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassInfoMapper {
    int deleteByPrimaryKey(Integer classId);

    int insert(ClassInfo record);

    int insertSelective(ClassInfo record);

    ClassInfo selectByPrimaryKey(Integer classId);

    int updateByPrimaryKeySelective(ClassInfo record);

    int updateByPrimaryKey(ClassInfo record);
}