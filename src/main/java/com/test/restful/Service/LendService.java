package com.test.restful.Service;

import com.test.restful.Dao.LendListMapper;
import com.test.restful.domain.LendList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LendService {
    @Autowired
    private LendListMapper lendListMapper;

    public boolean bookReturn(long bookId) {
        return lendListMapper.bookReturnOne(bookId) > 0 && lendListMapper.bookReturnTwo(bookId) > 0;
    }

    public boolean bookLend(long bookId, int readerId) {
        return lendListMapper.bookLendOne(bookId, readerId) > 0 && lendListMapper.bookLendTwo(bookId) > 0;
    }

    public ArrayList<LendList> lendList() {
        ArrayList<LendList> list = lendListMapper.lendList();

        return list;
    }

    public ArrayList<LendList> myLendList(int readerId) {
        return lendListMapper.myLendList(readerId);
    }

}
