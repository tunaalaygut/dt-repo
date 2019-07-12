package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.domain.RecordState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BuildingRepository extends CrudRepository<Building, Long> {
    Building findByBuildingName(String buildingName);
    //Building findByUpdateStatus(String buildingName);
    Iterable<Building> findAllByStateEquals(RecordState state);
}
