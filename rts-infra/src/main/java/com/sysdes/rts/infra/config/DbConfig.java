package com.sysdes.rts.infra.config;

import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import com.sysdes.rts.dal.adapter.RobotTrackerRepositoryAdapter;
import com.sysdes.rts.dal.repository.HoleRepository;
import com.sysdes.rts.dal.repository.RobotRepository;
import com.sysdes.rts.dal.storage.InMemoryStore;
import com.sysdes.rts.dal.storage.RobotStore;
import com.sysdes.rts.dal.storage.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashMap;
import java.util.HashSet;

@Configuration
@EnableJpaRepositories(basePackages = "com.sysdes.rts.dal.*")
public class DbConfig {

    @Bean("Store")
    @Profile("inMemory")
    public Store getInMemoryStore(){
        return new InMemoryStore(new HashMap<>(), new HashMap<>());
    }

    @Bean("Store")
    public Store getRobotStore(
            @Autowired final RobotRepository robotRepository,
            @Autowired final HoleRepository holeRepository
            ){
        return new RobotStore(robotRepository, holeRepository);
    }

    @Bean
    public RobotTrackerRepository getRobotTrackerRepository(final @Qualifier("Store") Store store){
        return new RobotTrackerRepositoryAdapter(store);
    }
}
