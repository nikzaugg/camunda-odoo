package ch.itsheinrich.teaching.camunda;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
// import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableProcessApplication
public class CamundaSpringBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(CamundaSpringBootStarter.class, args);
    }

    /*@Bean
    SpinProcessEnginePlugin spinProcessEnginePlugin() {
        return new SpinProcessEnginePlugin();
    }*/

}
