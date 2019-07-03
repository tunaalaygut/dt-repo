package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingStatus;
import com.alaygut.prototype.dto.AddMeetingStatusForm;

public interface MeetingStatusService {
	void addStatus(AddMeetingStatusForm addMeetingStatusForm);
	Iterable<MeetingStatus> getAllStatus();
}
