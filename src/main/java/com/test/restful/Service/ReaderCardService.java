package com.test.restful.Service;

import com.test.restful.Dao.ReaderCardMapper;
import com.test.restful.domain.ReaderCard;
import com.test.restful.domain.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderCardService {
    @Autowired
    private ReaderCardMapper readerCardMapper;

    public boolean addReaderCard(ReaderInfo readerInfo) {
        return readerCardMapper.insert(readerInfo) > 0;
    }

    public boolean updatePasswd(ReaderCard readerCard) {
        return readerCardMapper.rePassword(readerCard);
    }

    public boolean updateName(ReaderCard readerCard) {
        return readerCardMapper.updateName(readerCard);
    }

    public boolean deleteReaderCard(int readerId) {
        return readerCardMapper.deleteByPrimaryKey(readerId) > 0;
    }

}
