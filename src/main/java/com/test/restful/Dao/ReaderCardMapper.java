package com.test.restful.Dao;

import com.test.restful.domain.ReaderCard;
import com.test.restful.domain.ReaderInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderCardMapper {
    int deleteByPrimaryKey(Integer readerId);

    int insert(ReaderInfo record);

    int insertSelective(ReaderCard record);

    ReaderCard selectByPrimaryKey(Integer readerId);

    int updateByPrimaryKeySelective(ReaderCard record);

    int updateByPrimaryKey(ReaderCard record);

    int getMatchCount(final long readerId, String passwd);

    boolean rePassword(ReaderCard readerCard);

    boolean updateName(ReaderCard readerCard);
}