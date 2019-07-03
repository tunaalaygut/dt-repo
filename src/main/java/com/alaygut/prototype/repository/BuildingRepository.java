package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.Building; 
import org.springframework.data.repository.CrudRepository;

public interface BuildingRepository extends CrudRepository<Building, Long> {
}
