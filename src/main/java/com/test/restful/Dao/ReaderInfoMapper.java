package com.test.restful.Dao;

import com.test.restful.domain.ReaderInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReaderInfoMapper {
    int deleteByPrimaryKey(Integer readerId);

    int insert(ReaderInfo record);

    int insertSelective(ReaderInfo record);

    ReaderInfo selectByPrimaryKey(Integer readerId);

    int updateByPrimaryKeySelective(ReaderInfo record);

    int updateByPrimaryKey(ReaderInfo record);

    ArrayList<ReaderInfo> getAllReaderInfo();
}