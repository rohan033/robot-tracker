package com.sysdes.rts.infra.config;

import com.sysdes.rts.application.api.robot.ports.CreateHoleCommand;
import com.sysdes.rts.application.api.robot.ports.CreateRobotCommand;
import com.sysdes.rts.application.api.robot.ports.GetRobotCommand;
import com.sysdes.rts.application.api.robot.ports.MoveRobotCommand;
import com.sysdes.rts.application.service.HoleService;
import com.sysdes.rts.application.service.RobotTrackerService;
import com.sysdes.rts.application.spi.events.ports.EventPublisher;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import com.sysdes.rts.events.adapter.EventPublisherAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {
    @Bean
    public HoleService getHoleService(@Autowired final RobotTrackerRepository robotTrackerRepository){
        return new HoleService(robotTrackerRepository);
    }

    @Bean
    public EventPublisher getEventPublisher(){
        return new EventPublisherAdapter();
    }

    private RobotTrackerService getRobotTrackerService(
            @Autowired final RobotTrackerRepository robotTrackerRepository,
            final HoleService holeService,
            final EventPublisher eventPublisher){
        return new RobotTrackerService(robotTrackerRepository, holeService, eventPublisher);
    }

    @Bean("CreateRobotCommand")
    @Primary
    public CreateRobotCommand getCreateRobotCommand(
            @Autowired final RobotTrackerRepository robotTrackerRepository,
            final HoleService holeService,
            final EventPublisher eventPublisher){
        return (CreateRobotCommand) getRobotTrackerService(robotTrackerRepository, holeService, eventPublisher);
    }

    @Bean("GetRobotCommand")
    public GetRobotCommand getGetRobotCommand(
            @Autowired final RobotTrackerRepository robotTrackerRepository,
            final HoleService holeService,
            final EventPublisher eventPublisher){
        return (GetRobotCommand) getRobotTrackerService(robotTrackerRepository, holeService, eventPublisher);
    }

    @Bean("MoveRobotCommand")
    public MoveRobotCommand getMoveRobotCommand(
            @Autowired final RobotTrackerRepository robotTrackerRepository,
            final HoleService holeService,
            final EventPublisher eventPublisher){
        return (MoveRobotCommand) getRobotTrackerService(robotTrackerRepository, holeService, eventPublisher);
    }



}
