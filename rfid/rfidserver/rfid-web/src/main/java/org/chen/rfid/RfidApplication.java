package org.chen.rfid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({"org.chen.rfid.mapper.security","org.chen.rfid.mapper.station","org.chen.rfid.mapper.system"})
@EnableScheduling
public class RfidApplication {

    public static void main(String[] args) {
        SpringApplication.run(RfidApplication.class, args);
    }

}
