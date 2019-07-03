package com.alaygut.prototype.dto;

 
import java.time.LocalTime;


import javax.validation.constraints.FutureOrPresent;

import javax.validation.constraints.Size;

public class AddMeetingRequestForm {
	
	private Long meetingRoomId;
	
	private Long memberId;
	
	private Long meetingTypeId;
	
	@FutureOrPresent(message = "Verilen zaman aralığı")
	private LocalTime startTime;
	
	@FutureOrPresent(message = "Verilen zaman aralığı")
	private LocalTime endTime;
	
	@Size(min = 0, max = 250)
	private String description;
	
	private Long meetingStatusId;

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
	
	
}
