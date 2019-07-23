package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRequest; 
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.MeetingState;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.dto.MeetingDetail;
import com.alaygut.prototype.dto.MeetingRequestDetailProvider;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MeetingRequestService {
	boolean addRequest(AddMeetingRequestForm addMeetingRequestForm);
	Iterable<MeetingRequest> getAllRequests();
	Iterable<MeetingRequest> getAllActiveRequests();
	Iterable<MeetingRequest> getAllPendingRequests();
	Iterable<MeetingRequest> getAllMemberMeetingRequests(Member member);
	Iterable<MeetingRequest> getAllByDateAndMeetingRoomAndMeetingRequestState(LocalDate date, MeetingRoom meetingRoom, MeetingState state);
	MeetingRequest getMeetingRequest(Long meetingRequestId);
	List<MeetingDetail> getGridData(String date, String meetingRoomId);
	void deactivate(IDTransfer idTransfer);
	void edit(AddMeetingRequestForm addMeetingRequestForm);
	AddMeetingRequestForm getAddMeetingRequestForm();
	void setExternalData(AddMeetingRequestForm form);
	Map<String, String> getBuildingMeetingRooms(Long buildingId);
	MeetingRequestDetailProvider getListMeetingRequestsDetailProvider();
	MeetingRequestDetailProvider getPendingMeetingRequestsDetailProvider();
	MeetingRequestDetailProvider getMemberMeetingRequestDetailsProvider(Member member);
	int getNumberOfPendingRequests();
	void declineMeetingRequest(Long meetingRequestId, Long supervisorId);
	void acceptMeetingRequest(Long meetingRequestId, Long supervisorId);
	void cancel(Long meetingRequestId);
	boolean requestFromUser(AddMeetingRequestForm form);
	List<MeetingRequest> getMemberRequests(Member member);
	void acceptMemberMeetingRequest(Long meetingRequestId, Long memberId);
}
