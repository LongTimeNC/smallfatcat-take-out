package com.smallfatcat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zsz
 * @Description-项目的启动类
 * @date 2022/10/5
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class FatCatApplication {
    public static void main(String[] args) {
        SpringApplication.run(FatCatApplication.class, args);
        log.info("项目启动成功！");
    }
}
