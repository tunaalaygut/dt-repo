package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.Login;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<Login, Long> {
}
