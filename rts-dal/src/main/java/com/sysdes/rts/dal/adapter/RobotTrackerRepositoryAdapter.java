package com.sysdes.rts.dal.adapter;

import com.sysdes.rts.application.model.Robot;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import com.sysdes.rts.dal.storage.Store;
import com.sysdes.rts.dal.util.Mapper;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class RobotTrackerRepositoryAdapter implements RobotTrackerRepository {

    private final Store store;

    @Override
    public Robot save(Robot robot) {
        com.sysdes.rts.dal.entity.Robot entity = Mapper.toEntity(robot);
        return Mapper.toModel(store.save(entity));
    }

    @Override
    public Optional<com.sysdes.rts.application.model.Robot> findRobotByName(String name) {
        Optional<com.sysdes.rts.dal.entity.Robot> entity = store.findByName(name);
        return entity.map(Mapper::toModel);
    }
}
