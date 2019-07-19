package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.dto.*;

public interface ReasonService {
	void addReason(AddReasonForm addReasonForm);
	Iterable<Reason> getAllReasons();
	Iterable<Reason> getAllActiveReasons();
	Reason getReason(Long reasonId);
	void deactivate(IDTransfer idTransfer);
	boolean edit(AddReasonForm addReasonForm);
	AddReasonForm getEditForm(Long reasonId);
	Reason getById(AddMeetingStatusForm addMeetingStatusForm);
	//void fixAddForm(AddMeetingRoomForm addMeetingRoomForm);
}
