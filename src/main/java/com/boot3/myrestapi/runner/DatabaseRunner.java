package com.boot3.myrestapi.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
@Component
@Order(1)
public class DatabaseRunner implements ApplicationRunner {
    @Autowired
    DataSource dataSource;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("DataSource 구현객체는 ?? " + dataSource.getClass().getName());
        try (Connection connection = dataSource.getConnection()) {
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());
        }
    }
}