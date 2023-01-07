package com.sysdes.rts.infra.config;

import com.sysdes.rts.application.service.RobotTrackerService;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RobotTrackerService getRobotTrackerService(final RobotTrackerRepository robotTrackerRepository){
        return new RobotTrackerService(robotTrackerRepository);
    }

}
