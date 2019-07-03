package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.dto.AddReasonForm;

public interface ReasonService {
	void addReason(AddReasonForm addReasonForm);
	Iterable<Reason> getAllReasons();
}
