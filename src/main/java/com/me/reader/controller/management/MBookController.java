package com.me.reader.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    @GetMapping("/index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/management/book"); // 指向book.ftl
    }

    /**
     * wangEditor文件上传
     * @param file 上传文件
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
}
