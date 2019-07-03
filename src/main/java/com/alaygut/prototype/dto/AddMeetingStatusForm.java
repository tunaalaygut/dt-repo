package com.alaygut.prototype.dto;

import com.alaygut.prototype.domain.Reason;

import javax.validation.constraints.Size;

public class AddMeetingStatusForm {
	@Size(min = 5, max = 30, message ="Durum ismi [5-30] karakter uzunluğunda olmalıdır.")
	private String meetingStatusName;

	private Long reasonId;

	private Iterable<Reason> allReasons; //to be used to retrieve reasons from ReasonService.

	public String getMeetingStatusName() {
		return meetingStatusName;
	}

	public void setMeetingStatusName(String meetingStatusName) {
		this.meetingStatusName = meetingStatusName;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public Iterable<Reason> getAllReasons() {
		return allReasons;
	}

	public void setAllReasons(Iterable<Reason> allReasons) {
		this.allReasons = allReasons;
	}
}
