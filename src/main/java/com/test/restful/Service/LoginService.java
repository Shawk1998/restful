package com.test.restful.Service;

import com.test.restful.Dao.AdminMapper;
import com.test.restful.Dao.ReaderCardMapper;
import com.test.restful.Dao.ReaderInfoMapper;
import com.test.restful.domain.Admin;
import com.test.restful.domain.ReaderCard;
import com.test.restful.domain.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    ReaderCardMapper readerCardDao;
    @Autowired
    ReaderInfoMapper readerInfoDao;
    @Autowired
    AdminMapper adminDao;

    public boolean hasMatchReader(int readerId, String passwd) {
        return readerCardDao.getMatchCount(readerId, passwd) > 0;
    }

    public ReaderCard findReaderCardByUserId(int readerId) {
        return readerCardDao.selectByPrimaryKey(readerId);
    }

    public ReaderInfo findReaderInfoByReaderId(int readerId) {
        return readerInfoDao.selectByPrimaryKey(readerId);
    }

    public boolean hasMatchAdmin(int adminId, String password) {
        return adminDao.getMatchCount(adminId, password) == 1;
    }

    public boolean adminRePasswd(Admin admin) {
        return adminDao.updateByPrimaryKey(admin) > 0;
    }

    public String getAdminPasswd(int id) {
        return adminDao.getPassword(id);
    }

}
