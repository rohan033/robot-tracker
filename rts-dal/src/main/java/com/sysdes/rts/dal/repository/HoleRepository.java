package com.sysdes.rts.dal.repository;

import com.sysdes.rts.dal.entity.Hole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoleRepository extends JpaRepository<Hole, Integer> {
    Optional<Hole> findByXAndY(Integer x, Integer y);
}
