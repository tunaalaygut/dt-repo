package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.domain.RecordState;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRepository extends CrudRepository<Building, Long> {
    Iterable<Building> findAllByStateEquals(RecordState state);
}
