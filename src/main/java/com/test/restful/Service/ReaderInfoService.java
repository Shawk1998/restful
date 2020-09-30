package com.test.restful.Service;

import com.test.restful.Dao.ReaderCardMapper;
import com.test.restful.Dao.ReaderInfoMapper;
import com.test.restful.domain.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ReaderInfoService {
    @Autowired
    private ReaderInfoMapper readerInfoMapper;

    @Autowired
    private ReaderCardMapper readerCardMapper;
    public ArrayList<ReaderInfo> readerInfos() {
        return readerInfoMapper.getAllReaderInfo();
    }

    public boolean deleteReaderInfo(int readerId) {
        return readerInfoMapper.deleteByPrimaryKey(readerId) > 0;
    }

    public ReaderInfo getReaderInfo(int readerId) {
        return readerInfoMapper.selectByPrimaryKey(readerId);
    }

    public boolean editReaderInfo(ReaderInfo readerInfo) {
        return readerInfoMapper.updateByPrimaryKeySelective(readerInfo) > 0;
    }

    @Transactional
    public boolean addReaderInfo(ReaderInfo readerInfo) {
        readerCardMapper.insert(readerInfo);
        int i = readerInfoMapper.insert(readerInfo);
        return i>0;
    }
}
