package com.sysdes.rts.dal.repository;

import com.sysdes.rts.dal.entity.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RobotRepository extends JpaRepository<Robot, Integer> {
    Optional<Robot> findByName(String name);
}
