package org.course_registration;

import org.course_registration.dao.StuDOMapper;
import org.course_registration.dataobject.StuDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.StreamPrintService;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"org.course_registration"})
@RestController
@MapperScan("org.course_registration.dao")
public class App 
{
//    @Autowired
//    private StuDOMapper stuDOMapper;
//
//    @RequestMapping("/")
//    public String home() {
//        StuDO userDO = stuDOMapper.selectByPrimaryKey(1);
//        return userDO == null ? "user not exist" : userDO.getName();
//    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }
}
