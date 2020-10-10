package com.test.restful.Controller;

import com.test.restful.Service.BookService;
import com.test.restful.Service.LendService;
import com.test.restful.domain.BookInfo;
import com.test.restful.domain.ReaderCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LendController {
    @Autowired
    private LendService lendService;

    @Autowired
    private BookService bookService;

    @RequestMapping("/lendbook/{id}")
    public ModelAndView bookLend(@PathVariable long id,HttpServletRequest request) {
        BookInfo book = bookService.getBook(id);
        ModelAndView modelAndView = new ModelAndView("admin_book_lend");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @RequestMapping("/lendbookdo")
    public String bookLendDo(HttpServletRequest request, RedirectAttributes redirectAttributes, int readerId) {
        long bookId = Integer.parseInt(request.getParameter("id"));
        boolean lendsucc = lendService.bookLend(bookId, readerId);
        if (lendsucc) {
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功！");
            return "redirect:/allbooks";
        } else {
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功！");
            return "redirect:/allbooks";
        }


    }

    @RequestMapping("/returnbook/{id}")
    public String bookReturn(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        boolean retSucc = lendService.bookReturn(id);
        if (retSucc) {
            redirectAttributes.addFlashAttribute("succ", "图书归还成功！");
            return "redirect:/allbooks";
        } else {
            redirectAttributes.addFlashAttribute("error", "图书归还失败！");
            return "redirect:/allbooks";
        }
    }


    @RequestMapping("/lendlist")
    public ModelAndView lendList() {

        ModelAndView modelAndView = new ModelAndView("admin_lend_list");
        modelAndView.addObject("list", lendService.lendList());
        return modelAndView;
    }

    @RequestMapping("/mylend")
    public ModelAndView myLend(HttpServletRequest request) {
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("userCard");
        ModelAndView modelAndView = new ModelAndView("reader_lend_list");
        modelAndView.addObject("list", lendService.myLendList(readerCard.getReaderId()));
        return modelAndView;
    }
}
