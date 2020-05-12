package com.tmdrk.amqp;

import com.tmdrk.amqp.listener.RabbitMqListener;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableRabbit
@SpringBootApplication
public class TmdrkSpringbootAmqpApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TmdrkSpringbootAmqpApplication.class);
        springApplication.addListeners(new RabbitMqListener());
        springApplication.run(args);
    }

}
