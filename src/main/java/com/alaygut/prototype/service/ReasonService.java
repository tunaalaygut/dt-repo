package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.dto.AddReasonForm;
import com.alaygut.prototype.dto.IDTransfer;

public interface ReasonService {
	void addReason(AddReasonForm addReasonForm);
	Iterable<Reason> getAllReasons();
	Iterable<Reason> getAllActiveReasons();
	void deactivate(IDTransfer idTransfer);
}
