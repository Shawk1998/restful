package com.test.restful.Service;

import com.test.restful.Controller.RedisUtil;
import com.test.restful.Dao.BookInfoMapper;
import com.test.restful.domain.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    RedisUtil redisUtil;

    public ArrayList<BookInfo> queryBook(String searchWord) {
        searchWord="%"+searchWord+"%";
        ArrayList<BookInfo> arrayList = bookInfoMapper.queryBook(searchWord);
        return arrayList;
    }

    public ArrayList<BookInfo> getAllBooks() {
        if (!redisUtil.hasKey("books")) {
            ArrayList<BookInfo> arrayList = bookInfoMapper.getAllBooks();
           // redisUtil.set("books", arrayList, 30);
            for (BookInfo book:arrayList
                 ) {
                redisUtil.hset("book",book.getBookId()+"",book,30);
            }
            System.out.println("数据库拿");
            return arrayList;
        } else {
            ArrayList<BookInfo> arrayList = (ArrayList) redisUtil.hget("books");
            System.out.println("redis拿");
            return arrayList;
        }
    }

    public int deleteBook(long bookId) {
        return bookInfoMapper.deleteByPrimaryKey(bookId);
    }

    public boolean matchBook(String searchWord) {
        searchWord="%"+searchWord+"%";
        return bookInfoMapper.matchBook(searchWord) > 0;
    }

    public boolean addBook(BookInfo book) {
        return bookInfoMapper.insert(book) > 0;
    }

    public BookInfo getBook(Long bookId) {
        BookInfo bookInfo;
        if(redisUtil.hHasKey("books",bookId+"")){
            bookInfo=(BookInfo) redisUtil.hget("books",bookId+"");
            System.out.println("redis取");
        }else {
            bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
            redisUtil.hset("books",bookInfo.getBookId()+"",bookInfo,30);
            System.out.println("数据库取");
        }
        return bookInfo;
    }

    public boolean editBook(BookInfo book) {
        int a=bookInfoMapper.updateByPrimaryKeySelective(book);
        return a>0;
    }
}
