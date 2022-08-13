package cn.windors.malody;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Windor
 */
@SpringBootApplication
@MapperScan("cn.windors.malody.mapper")
public class MalodyApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MalodyApiApplication.class, args);
    }
}