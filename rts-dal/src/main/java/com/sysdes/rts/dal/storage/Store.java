package com.sysdes.rts.dal.storage;

import com.sysdes.rts.dal.entity.Robot;

import java.util.Optional;

public interface Store {
    Robot save(Robot robot);

    Optional<Robot> findByName(String name);
}
