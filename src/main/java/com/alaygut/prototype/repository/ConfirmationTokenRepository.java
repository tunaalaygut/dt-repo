package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}

