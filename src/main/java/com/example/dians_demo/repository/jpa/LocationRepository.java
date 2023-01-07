package com.example.dians_demo.repository.jpa;

import com.example.dians_demo.model.Enum.LocationEnum;
import com.example.dians_demo.model.Location;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
   Optional<Location> findByName(String name);
   Optional<Location> findByLocationType(LocationEnum locationType);

    List<Location> findByNameIgnoreCaseContaining(String keyword);
}
