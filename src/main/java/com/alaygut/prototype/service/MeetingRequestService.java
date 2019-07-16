package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;

import java.util.Map;

public interface MeetingRequestService {
	void addRequest(AddMeetingRequestForm addMeetingRequestForm);
	Iterable<MeetingRequest> getAllRequests();
	Iterable<MeetingRequest> getAllActiveRequests();
	MeetingRequest getMeetingRequest(Long meetingRequestId);
	void deactivate(IDTransfer idTransfer);
	void edit(AddMeetingRequestForm addMeetingRequestForm);
	AddMeetingRequestForm getAddMeetingRequestForm();
	void setExternalData(AddMeetingRequestForm form);
	Map<String, String> getBuildingMeetingRooms(Long buildingId);
}
