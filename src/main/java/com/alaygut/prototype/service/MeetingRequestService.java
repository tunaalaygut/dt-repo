package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.dto.MeetingRequestDetailProvider;

import java.util.Map;

public interface MeetingRequestService {
	void addRequest(AddMeetingRequestForm addMeetingRequestForm);
	Iterable<MeetingRequest> getAllRequests();
	Iterable<MeetingRequest> getAllActiveRequests();
	Iterable<MeetingRequest> getAllPendingRequests();
	Iterable<MeetingRequest> getAllMemberMeetingRequests(Member member);
	MeetingRequest getMeetingRequest(Long meetingRequestId);
	void deactivate(IDTransfer idTransfer);
	void edit(AddMeetingRequestForm addMeetingRequestForm);
	AddMeetingRequestForm getAddMeetingRequestForm();
	void setExternalData(AddMeetingRequestForm form);
	Map<String, String> getBuildingMeetingRooms(Long buildingId);
	MeetingRequestDetailProvider getListMeetingRequestsDetailProvider();
	MeetingRequestDetailProvider getPendingMeetingRequestsDetailProvider();
	MeetingRequestDetailProvider getMemberMeetingRequestDetailsProvider(Member member);
	int getNumberOfPendingRequests();
	void declineMeetingRequest(Long meetingRequestId);
	void acceptMeetingRequest(Long meetingRequestId);
}
