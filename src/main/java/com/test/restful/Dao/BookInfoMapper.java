package com.test.restful.Dao;

import com.test.restful.domain.BookInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookInfoMapper {
    int deleteByPrimaryKey(Long bookId);

    int insert(BookInfo record);

    int insertSelective(BookInfo record);

    BookInfo selectByPrimaryKey(Long bookId);

    int updateByPrimaryKeySelective(BookInfo record);

    int updateByPrimaryKeyWithBLOBs(BookInfo record);

    int updateByPrimaryKey(BookInfo record);

    ArrayList<BookInfo> getAllBooks();

    ArrayList<BookInfo> queryBook(final String searchWord);

    int matchBook(final String searchWord);
}