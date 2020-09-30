package com.test.restful.Controller;

import com.test.restful.Service.LoginService;
import com.test.restful.Service.ReaderCardService;
import com.test.restful.Service.ReaderInfoService;
import com.test.restful.domain.ReaderCard;
import com.test.restful.domain.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class ReaderController {
    @Autowired
    private ReaderInfoService readerInfoService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ReaderCardService readerCardService;

    @RequestMapping("allreaders")
    public ModelAndView allBooks() {
        ArrayList<ReaderInfo> readers = readerInfoService.readerInfos();
        ModelAndView modelAndView = new ModelAndView("admin_readers");
        modelAndView.addObject("readers", readers);
        return modelAndView;
    }

    //!改
    @RequestMapping("reader_delete")
    public String readerDelete(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        int readerId = Integer.parseInt(request.getParameter("readerId"));
        boolean success = readerInfoService.deleteReaderInfo(readerId);
        boolean succ = readerCardService.deleteReaderCard(readerId);

        if (success && succ) {
            redirectAttributes.addFlashAttribute("succ", "删除成功！");
            return "redirect:/allreaders";
        } else {
            redirectAttributes.addFlashAttribute("error", "删除失败！");
            return "redirect:/allreaders";
        }

    }

    @RequestMapping("/reader_info")
    public ModelAndView toReaderInfo(HttpServletRequest request) {
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("userCard");
        ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerCard.getReaderId());
        ModelAndView modelAndView = new ModelAndView("reader_info");
        modelAndView.addObject("readerinfo", readerInfo);
        return modelAndView;
    }

    @RequestMapping("reader_edit")
    public ModelAndView readerInfoEdit(HttpServletRequest request) {
        int readerId = Integer.parseInt(request.getParameter("readerId"));
        ReaderInfo readerInfo = readerInfoService.getReaderInfo(readerId);
        ModelAndView modelAndView = new ModelAndView("admin_reader_edit");
        modelAndView.addObject("readerInfo", readerInfo);
        return modelAndView;
    }

    @RequestMapping("reader_edit_do")
    public String readerInfoEditDo(HttpServletRequest request, ReaderInfo readerInfo, RedirectAttributes redirectAttributes) {
        readerInfo.setReaderId(Integer.parseInt(request.getParameter("id")));
        boolean succ = readerInfoService.editReaderInfo(readerInfo);
        if (succ) {
            redirectAttributes.addFlashAttribute("succ", "读者信息修改成功！");
            return "redirect:/allreaders";
        } else {
            redirectAttributes.addFlashAttribute("error", "读者信息修改失败！");
            return "redirect:/allreaders";
        }
    }

    @RequestMapping("reader_add")
    public ModelAndView readerInfoAdd() {
        ModelAndView modelAndView = new ModelAndView("admin_reader_add");
        return modelAndView;

    }

    //用户功能
    @RequestMapping("reader_repasswd")
    public ModelAndView readerRePasswd() {
        ModelAndView modelAndView = new ModelAndView("reader_repasswd");
        return modelAndView;
    }

    @RequestMapping("reader_repasswd_do")
    public String readerRePasswdDo(HttpServletRequest request, String oldPasswd, String newPasswd, String reNewPasswd, RedirectAttributes redirectAttributes) {
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("userCard");
        int readerId = readerCard.getReaderId();
        String passwd = readerCard.getPasswd();

        if (newPasswd.equals(reNewPasswd)) {
            if (passwd.equals(oldPasswd)) {
                readerCard.setPasswd(passwd);
                boolean succ = readerCardService.updatePasswd(readerCard);
                if (succ) {
                    ReaderCard readerCardNew = loginService.findReaderCardByUserId(readerId);
                    request.getSession().setAttribute("userCard", readerCardNew);
                    redirectAttributes.addFlashAttribute("succ", "密码修改成功！");
                    return "redirect:/reader_repasswd";
                } else {
                    redirectAttributes.addFlashAttribute("succ", "密码修改失败！");
                    return "redirect:/reader_repasswd";
                }

            } else {
                redirectAttributes.addFlashAttribute("error", "修改失败,原密码错误");
                return "redirect:/reader_repasswd";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "修改失败,两次输入的新密码不相同");
            return "redirect:/reader_repasswd";
        }
    }
    @RequestMapping("reader_add_do")
    public String readerInfoAddDo(ReaderInfo readerInfo,RedirectAttributes redirectAttributes){
        boolean succ = readerInfoService.addReaderInfo(readerInfo);
        if (succ) {
            redirectAttributes.addFlashAttribute("succ", "添加读者信息成功！");
            return "redirect:/allreaders";
        } else {
            redirectAttributes.addFlashAttribute("succ", "添加读者信息失败！");
            return "redirect:/allreaders";
        }
    }
}