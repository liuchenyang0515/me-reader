package com.me.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    // 找到beanId为kaptchaProducer的类注入，在applicationContext.xml有配置
    private Producer kaptchaProducer;

    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 响应立即过期
        response.setDateHeader("Expires", 0);
        // 不缓存任何图片数据
        /**
         * no-cache，并不是指不能用 cache，客户端仍会把带有 no-cache 的响应缓存下来，只不过每次不会直接用缓存，而得先 revalidate 一下，
         * 所以其实no-cache真正合适的名字才是 must-revalidate。而现在的must-revalidate更合适的名字可能是 never-return-stale。
         * 如果你想让客户端完全不缓存响应，应该用no-store，带有no-store的响应不会被缓存到任意的磁盘或者内存里，它才是真正的 no-cache。
         *
         * 各种缓存服务器软件，比如 NGINX、Vanish、Squid 都或多或少的允许通过Cache-Control指令或者修改软件配置的方式返回过期缓存，
         * 同时它们也都遵循了 HTTP 规范，加上must-revalidate的确能阻止返回过期缓存的行为。
         * 国内各大 CDN 厂商应该用的都是自研软件，不确定支持不支持返回过期缓存，所以 must-revalidate在国内网络环境能不能派上用场也不太确定。
         *
         * 上面只讲了must-revalidate在缓存服务器上的作用，还没说在浏览器上的作用。既然我说了must-revalidate更合适的名字是 never-return-stale，
         * 那浏览器有没有 return stale 的情况呢，也就是说浏览器会不会使用过期缓存呢？
         * 还真有，那就是浏览器的后退前进功能。当点击 back/forwrad 按钮时，浏览器会尽量用本地缓存来重新打开页面，即便缓存已经过期了，也不会 revalidate。
         * 那must-revalidate能阻止这一行为，强迫该缓存 revalidate 吗？答案是并不能，甚至no-cache也不行，
         * 只有比no-cache更强劲的no-store才可以，因为硬盘上都没有缓存，浏览器想用也没法用啊。
         *
         * 总结：must-revalidate在缓存服务器上有一点点作用，但比较小众；在浏览器端几乎没有任何作用。
         * post-check=0, pre-check=0出于兼容IE的考虑，现在用不到
         * setHeader不要同一个属性设置几次，后面会覆盖前面
         *
         * 实验结果：
         * 如果只有response.setHeader("Pragma", "no-cache");
         * 或者只有response.setHeader("Cache-Control", "no-cache");
         * 或者只有response.setHeader("Cache-Control", "must-revalidate");
         *
         * 虽然刷新会重新发送请求，但是浏览器的back/forwrad点击按钮还是会from disk cache读取
         * 只有设置response.setHeader("Cache-Control", "no-store");不管是刷新还是浏览器的back/forwrad按钮，都会重新获取请求，刷新验证码
         */
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");

        response.setHeader("Pragma", "no-cache"); // 兼容http1.0，Cache-Control是http1.1才提供
        response.setContentType("image/png");
        // 生成验证码字符文本
        String verifyCode = kaptchaProducer.createText();
        request.getSession().setAttribute("kaptchaVerifyCode", verifyCode);
        // 打印，方便程序调试
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));
        BufferedImage image = kaptchaProducer.createImage(verifyCode);// 根据文本创建验证码图片
        ServletOutputStream out = response.getOutputStream();
        // 将image放在out输出流，格式为png，通过这一句话能将图片由服务端发送到浏览器
        ImageIO.write(image, "png", out); // 输出图片流
        out.flush(); // 立即输出
        out.close();
    }
}
