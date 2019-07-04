package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingStatus;
import com.alaygut.prototype.dto.AddMeetingStatusForm;
import com.alaygut.prototype.dto.IDTransfer;

public interface MeetingStatusService {
	void addStatus(AddMeetingStatusForm addMeetingStatusForm);
	Iterable<MeetingStatus> getAllStatus();
	Iterable<MeetingStatus> getAllActiveStatus();
	void deactivate(IDTransfer idTransfer);
}
