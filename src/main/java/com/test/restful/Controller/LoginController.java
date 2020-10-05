package com.test.restful.Controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.test.restful.Service.LoginService;
import com.test.restful.domain.Admin;
import com.test.restful.domain.ReaderCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@Controller
public class LoginController {
    @Autowired
    private DefaultKaptcha captchaProducer = null;
    @Autowired
    private LoginService loginService;

    private static LoginServlet usercard=new LoginServlet();

    public static String decode(String message, String key) {
        key = key + key + key + key;
        if (message.length() < 1)
            return null;
        byte[] byteRresult = new byte[message.length() / 2];
        for (int i = 0; i < message.length() / 2; i++) {
            int high = Integer.parseInt(message.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(message.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        try {
//           KeyGenerator kgen = KeyGenerator.getInstance("AES");
//           SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//           secureRandom.setSeed(key.getBytes());
//           kgen.init(128, secureRandom);
//           SecretKey secretKey = kgen.generateKey();
//           byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);//Decrypt_mode指解密操作
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result, "utf-8");//不加utf-8，中文时会乱码
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/verifyImage")
    public void getVerifyImage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        //生成验证码文本
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        System.out.println(capText);
        //利用生成的字符串构建图片
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
//        OutputStream ops=new FileOutputStream(new File("H:/Test/"+capText+".jpg"));
//        ImageIO.write(bi,"jpg",ops);

        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @PostMapping(value = "/loginCheck")
    public @ResponseBody
    Object loginCheck(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String id_a = id+"";
        String passwd = request.getParameter("passwd");
        String code = request.getParameter("code");
        String password;
        password = decode(passwd, code);
        HashMap<String, String> res = new HashMap<String, String>();

        String a = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
        if (!code.equals(a)) {
            res.put("stateCode", "-1");
            res.put("msg", "验证码错误");
            return res;
        }
        //request.getSession().setAttribute("user",passwd);
        boolean isReader = loginService.hasMatchReader(id, password);
        boolean isAdmin = loginService.hasMatchAdmin(id, password);
        if (!isAdmin && !isReader) {
            res.put("stateCode", "0");
            res.put("msg", "账号或密码错误！");
        } else if (isAdmin) {
            request.getSession().setAttribute("user", id_a);
            usercard.mLogin(id_a, request.getSession());
            Admin admin = new Admin();
            admin.setAdminId(id);
            admin.setPassword(password);
            request.getSession().setAttribute("userCard", admin);
            res.put("stateCode", "1");
            res.put("msg", "管理员登陆成功！");
        } else {
            request.getSession().setAttribute("user", id_a);
            usercard.mLogin(id_a, request.getSession());
            ReaderCard readerCard = loginService.findReaderCardByUserId(id);
            request.getSession().setAttribute("userCard", readerCard);
            res.put("stateCode", "2");
            res.put("msg", "读者登陆成功！");
        }
        return res;
    }

    @RequestMapping("/admin_main")
    public ModelAndView toAdminMain(HttpServletResponse response) {
        return new ModelAndView("admin_main");
    }

    @RequestMapping("/reader_main")
    public ModelAndView toReaderMain(HttpServletResponse response) {

        return new ModelAndView("reader_main");
    }

    @RequestMapping("/admin_repasswd")
    public ModelAndView reAdminPasswd() {

        return new ModelAndView("admin_repasswd");
    }

    @RequestMapping("/admin_repasswd_do")
    public String reAdminPasswdDo(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Admin admin = (Admin) request.getSession().getAttribute("userCard");
        String passwd = admin.getPassword();
        System.out.println(admin.getAdminId());
        String oldPasswd=request.getParameter("oldPasswd");
        String newPasswd=request.getParameter("newPasswd");
        System.out.println(passwd);
        System.out.println("old:"+oldPasswd);
        System.out.println("new:"+newPasswd);
        if (passwd.equals(oldPasswd)) {
            admin.setPassword(newPasswd);
            boolean succ = loginService.adminRePasswd(admin);
            if (succ) {
                redirectAttributes.addFlashAttribute("succ", "密码修改成功！");
                return "redirect:/admin_repasswd";
            } else {
                System.out.println("fail");
                admin.setPassword(oldPasswd);
                redirectAttributes.addFlashAttribute("error", "密码修改失败！");
                return "redirect:/admin_repasswd";
            }
        } else {
            System.out.println("erro");
            redirectAttributes.addFlashAttribute("error", "旧密码错误！");
            return "redirect:/admin_repasswd";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping("/login")
    public String toLogin(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

}