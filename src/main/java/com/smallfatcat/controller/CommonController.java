package com.smallfatcat.controller;

import com.smallfatcat.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author zsz
 * @Description-文件上传和下载
 * @date 2022/10/6
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${smallfatcat.path}")
    private String basePath;

    /**
     * 文件上传的方法
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info(file.toString());
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取原始文件的后缀名称
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名称,防止文件名称重复，造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        //判断是否存在该文件夹
        File dir = new File(basePath);
        if (!dir.exists()) {
            //如果不存在则创建文件夹
            dir.mkdirs();
        }
        try {
            //将文件转存到制定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(fileName);
    }

    @GetMapping("/download")
    public void downLoad(HttpServletResponse response, String name) {
        try {
            //通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
