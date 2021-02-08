package com.me.reader.controller.management;

import com.me.reader.entity.Book;
import com.me.reader.service.BookService;
import com.me.reader.service.exception.BussinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
public class MBookController {
    @Resource
    private BookService bookService;

    @GetMapping("/index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/management/book"); // 指向book.ftl
    }

    /**
     * wangEditor文件上传
     *
     * @param file    上传文件
     * @param request 原生请求对象
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    // editor.config.uploadFileName = 'img'; //图片上传时的参数名
    // 这里img是和图片上传时参数名对应
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        // 得到上传目录 /D:/me-reader/out/artifacts/me_reader_Web_exploded//upload/
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";// 运行时获取的路径是out目录下的路径
        // 文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 扩展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 另存为，保存文件到upload目录
        file.transferTo(new File(uploadPath + fileName + suffix));
        Map result = new HashMap();
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book) {
        Map result = new HashMap();
        try {
            book.setEvaluationQuantity(0); // 刚创建的书籍评分人数为0
            book.setEvaluationScore(0f); // 刚创建的书籍得分为0
            Document doc = Jsoup.parse(book.getDescription());// 解析图书详情
            //  <img src="/upload/20210208170456107.png" style="max-width:100%;">
            Element img = doc.select("img").first();// 获取图书详情第一图的元素对象
            String cover = img.attr("src");
            //   /upload/20210208170456107.png
            book.setCover(cover); // 来自于description描述的第一幅图
            bookService.createBook(book);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }
}
