package com.qf.ruigie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieApp {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApp.class,args);
        log.info("莫莫项目启动成功..");
    }
}
