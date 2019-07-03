package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.dto.AddMeetingRequestForm;

public interface MeetingRequestService {
	void addRequest(AddMeetingRequestForm addMeetingRequestForm);
	Iterable<MeetingRequest> getAllRequests();
	Iterable<MeetingRequest> getAllActiveRequests();
}
