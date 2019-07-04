package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;

public interface MeetingRequestService {
	void addRequest(AddMeetingRequestForm addMeetingRequestForm);
	Iterable<MeetingRequest> getAllRequests();
	Iterable<MeetingRequest> getAllActiveRequests();
	void deactivate(IDTransfer idTransfer);
}
