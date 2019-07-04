package com.alaygut.prototype.dto;

 
import java.time.LocalTime;


import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.MeetingStatus;
import com.alaygut.prototype.domain.MeetingType;
import com.alaygut.prototype.domain.Member;

public class AddMeetingRequestForm {
	
	@NotNull(message = "Toplantı isteği için bir oda seçilmelidir.")
	private Long meetingRoomId;
	
	@NotNull(message = "Toplantı isteği bir üye tarafından ayarlanmalıdır.")
	private Long memberId;
	
	@NotNull(message = "Toplantı isteği bir türe sahip olmalıdır.")
	private Long meetingTypeId;
	
	@FutureOrPresent(message = "Toplantı isteğinin bir başlangıç zamanı olmalıdır")
	private LocalTime startTime;
	
	@FutureOrPresent(message = "Toplantı isteğinin bir bitiş zamanı olmalıdır")
	private LocalTime endTime;
	
	@Size(min = 0, max = 250)
	private String description;
	
	@NotNull(message = "Toplantı isteğinin bir durumu olmalıdır.")
	private Long meetingStatusId;
	
	private Iterable<MeetingRoom> allMeetingRoom;
	
	private Iterable<Member> allMember;
	
	private Iterable<MeetingType> allMeetingType;
	
	private Iterable<MeetingStatus> allMeetingStatus;

	public Long getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(Long meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMeetingTypeId() {
		return meetingTypeId;
	}

	public void setMeetingTypeId(Long meetingTypeId) {
		this.meetingTypeId = meetingTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getMeetingStatusId() {
		return meetingStatusId;
	}

	public void setMeetingStatusId(Long meetingStatusId) {
		this.meetingStatusId = meetingStatusId;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Iterable<MeetingRoom> getAllMeetingRoom() {
		return allMeetingRoom;
	}

	public void setAllMeetingRoom(Iterable<MeetingRoom> allMeetingRoom) {
		this.allMeetingRoom = allMeetingRoom;
	}

	public Iterable<Member> getAllMember() {
		return allMember;
	}

	public void setAllMember(Iterable<Member> allMember) {
		this.allMember = allMember;
	}

	public Iterable<MeetingType> getAllMeetingType() {
		return allMeetingType;
	}

	public void setAllMeetingType(Iterable<MeetingType> allMeetingType) {
		this.allMeetingType = allMeetingType;
	}

	public Iterable<MeetingStatus> getAllMeetingStatus() {
		return allMeetingStatus;
	}

	public void setAllMeetingStatus(Iterable<MeetingStatus> allMeetingStatus) {
		this.allMeetingStatus = allMeetingStatus;
	}
	
	
}
