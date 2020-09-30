package com.test.restful.Controller;

import com.test.restful.Service.BookService;
import com.test.restful.domain.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/querybook")
    public ModelAndView queryBookDo(HttpServletRequest request, String searchWord) {
        boolean exist = bookService.matchBook(searchWord);
        if (exist) {
            ArrayList<BookInfo> books = bookService.queryBook(searchWord);
            ModelAndView modelAndView = new ModelAndView("admin_books");
            modelAndView.addObject("books", books);
            return modelAndView;
        } else {
            return new ModelAndView("admin_books", "error", "没有匹配的图书");
        }
    }
    @RequestMapping("/allbooks")
    public ModelAndView allBook() {
        ArrayList<BookInfo> books = new ArrayList<BookInfo>();
//        books= (ArrayList<Book>)redisTemplate.opsForValue().get("books");
//        if(books==null){
//            books=bookService.getAllBooks();
//        }else{
//            System.out.println("redis拿");
//        }
        books = bookService.getAllBooks();
//        System.out.println("abc"+books.get(1).getClassId());
        ModelAndView modelAndView = new ModelAndView("admin_books");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/reader_querybook")
    public ModelAndView readerQueryBook() {
        return new ModelAndView("reader_book_query");

    }

    @RequestMapping("/reader_querybook_do")
    public String readerQueryBookDo(HttpServletRequest request, String searchWord, RedirectAttributes redirectAttributes) {
        boolean exist = bookService.matchBook(searchWord);
        if (exist) {
            ArrayList<BookInfo> books = bookService.queryBook(searchWord);
            redirectAttributes.addFlashAttribute("books", books);
            return "redirect:/reader_querybook";
        } else {
            redirectAttributes.addFlashAttribute("error", "没有匹配的图书！");
            return "redirect:/reader_querybook";
        }

    }

    @RequestMapping("/deletebook")
    public String deleteBook(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        int res = bookService.deleteBook(bookId);
        //redisTemplate.delete("books");
        if (res == 1) {
            redirectAttributes.addFlashAttribute("succ", "图书删除成功！");
            return "redirect:/allbooks";
        } else {
            redirectAttributes.addFlashAttribute("error", "图书删除失败！");
            return "redirect:/allbooks";
        }
    }

    @RequestMapping("/book_add")
    public ModelAndView addBook(HttpServletRequest request) {

        return new ModelAndView("admin_book_add");

    }

    @RequestMapping("/book_add_do")
    public String addBookDo(BookInfo bookInfo, RedirectAttributes redirectAttributes) {
        boolean succ = bookService.addBook(bookInfo);
       // ArrayList<BookInfo> books = bookService.getAllBooks();
        if (succ) {
            redirectAttributes.addFlashAttribute("succ", "图书添加成功！");
            return "redirect:/allbooks";
        } else {
            redirectAttributes.addFlashAttribute("succ", "图书添加失败！");
            return "redirect:/allbooks";
        }
    }

    @RequestMapping("/updatebook")
    public ModelAndView bookEdit(HttpServletRequest request) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        BookInfo book = bookService.getBook(bookId);
        ModelAndView modelAndView = new ModelAndView("admin_book_edit");
        modelAndView.addObject("detail", book);
        return modelAndView;
    }

    @RequestMapping("/book_edit_do")
    public String bookEditDo(HttpServletRequest request, BookInfo book, RedirectAttributes redirectAttributes) {
        book.setBookId(Long.parseLong(request.getParameter("id")));
        boolean succ = bookService.editBook(book);
        if (succ) {
            redirectAttributes.addFlashAttribute("succ", "图书修改成功！");
            return "redirect:/allbooks";
        } else {
            redirectAttributes.addFlashAttribute("error", "图书修改失败！");
            return "redirect:/allbooks";
        }
    }

    @RequestMapping("/bookdetail")
    public ModelAndView bookDetail(HttpServletRequest request) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        BookInfo book = bookService.getBook(bookId);
        ModelAndView modelAndView = new ModelAndView("admin_book_detail");
        modelAndView.addObject("detail", book);
        return modelAndView;
    }

    @RequestMapping("/readerbookdetail")
    public ModelAndView readerBookDetail(HttpServletRequest request) {
        long bookId = Integer.parseInt(request.getParameter("bookId"));
        BookInfo book = bookService.getBook(bookId);
        ModelAndView modelAndView = new ModelAndView("reader_book_detail");
        modelAndView.addObject("detail", book);
        return modelAndView;
    }
}
