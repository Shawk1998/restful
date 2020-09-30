package com.test.restful.Service;

import com.test.restful.Dao.AdminMapper;
import com.test.restful.Dao.ReaderInfoMapper;
import com.test.restful.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;

    public Admin get(int id){
        id=123456;
        return adminMapper.selectByPrimaryKey(id);
    }

}
