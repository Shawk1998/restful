package com.test.restful.Dao;

import com.test.restful.domain.LendList;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface LendListMapper {
    int bookReturnOne(long bookId);

    int bookReturnTwo(long bookId);

    int bookLendOne(long bookId, int readerId);

    int bookLendTwo(long bookId);

    ArrayList<LendList> lendList();

    ArrayList<LendList> myLendList(final long readerId);
}