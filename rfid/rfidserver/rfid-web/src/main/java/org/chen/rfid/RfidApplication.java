package org.chen.rfid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"org.chen.rfid.mapper.security","org.chen.rfid.mapper.station"})
public class RfidApplication {

    public static void main(String[] args) {
        SpringApplication.run(RfidApplication.class, args);
    }

}
