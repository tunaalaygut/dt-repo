package com.alaygut.prototype.domain;

import javax.persistence.Column;   
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class MeetingStatus extends BaseClass{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meetingStatusId", nullable = false, updatable = false)
	private Long meetingStatusId;
	
	@Column(name = "meetingStatusName")
	private String meetingStatusName;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "reasonId")
	private Reason reason;
	
	public MeetingStatus() {} //default constructor
	
	public MeetingStatus(String meetingStatusName, Reason reason) {
		this.meetingStatusName = meetingStatusName;
		this.reason = reason;
	}

	public Long getMeetingStatusId() {
		return meetingStatusId;
	}

	public void setMeetingStatusId(Long meetingStatusId) {
		this.meetingStatusId = meetingStatusId;
	}

	public String getMeetingStatusName() {
		return meetingStatusName;
	}

	public void setMeetingStatusName(String meetingStatusName) {
		this.meetingStatusName = meetingStatusName;
	}


	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}
	
	
}
