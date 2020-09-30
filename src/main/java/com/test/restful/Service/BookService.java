package com.test.restful.Service;

import com.test.restful.Dao.BookInfoMapper;
import com.test.restful.domain.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {
    @Autowired
    private BookInfoMapper bookInfoMapper;

    public ArrayList<BookInfo> queryBook(String searchWord) {
        searchWord="%"+searchWord+"%";
        ArrayList<BookInfo> arrayList = bookInfoMapper.queryBook(searchWord);
        return arrayList;
    }

    public ArrayList<BookInfo> getAllBooks() {
        ArrayList<BookInfo> arrayList = bookInfoMapper.getAllBooks();
        return arrayList;
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
        BookInfo book = bookInfoMapper.selectByPrimaryKey(bookId);
        return book;
    }

    public boolean editBook(BookInfo book) {
        int a=bookInfoMapper.updateByPrimaryKeySelective(book);
        return a>0;
    }
}
