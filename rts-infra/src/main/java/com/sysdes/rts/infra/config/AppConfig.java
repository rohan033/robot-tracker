package com.sysdes.rts.infra.config;

import com.sysdes.rts.application.service.HoleService;
import com.sysdes.rts.application.service.RobotTrackerService;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    // TODO: should ideally create Beans for individual commands

    @Bean
    public HoleService getHoleService(@Autowired final RobotTrackerRepository robotTrackerRepository){
        return new HoleService(robotTrackerRepository);
    }

    @Bean
    public RobotTrackerService getRobotTrackerService(
            @Autowired final RobotTrackerRepository robotTrackerRepository, final HoleService holeService){
        return new RobotTrackerService(robotTrackerRepository, holeService);
    }

}
