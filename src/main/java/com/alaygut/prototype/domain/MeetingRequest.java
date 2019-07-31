package com.alaygut.prototype.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDate;   
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class MeetingRequest extends BaseClass{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meetingRequestId", nullable = false, updatable = false)
	private Long meetingRequestId;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "meetingRoomId")
	private MeetingRoom meetingRoom;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "memberId")
	private Member member;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "meetingTypeId")
	private MeetingType meetingType;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "startTime")
	private LocalTime startTime;
	
	@Column(name = "endTime")
	private LocalTime endTime;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "meetingRequestState")
	private MeetingState meetingRequestState;

	@ManyToOne
	@JoinColumn(name = "requestMadeTo")
	private Member requestMadeTo;

	public MeetingRequest() {
	}

	public MeetingRequest(MeetingRoom meetingRoom, Member member, MeetingType meetingType, LocalDate date, LocalTime startTime, LocalTime endTime, String description, MeetingState meetingState) {
		this.meetingRoom = meetingRoom;
		this.member = member;
		this.meetingType = meetingType;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.meetingRequestState = meetingState;
	}

	public Long getMeetingRequestId() {
		return meetingRequestId;
	}

	public void setMeetingRequestId(Long meetingRequestId) {
		this.meetingRequestId = meetingRequestId;
	}

	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public MeetingType getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MeetingState getMeetingRequestState() {
		return meetingRequestState;
	}

	public void setMeetingRequestState(MeetingState meetingRequestState) {
		this.meetingRequestState = meetingRequestState;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Member getRequestMadeTo() {
		return requestMadeTo;
	}

	public void setRequestMadeTo(Member requestMadeTo) {
		this.requestMadeTo = requestMadeTo;
	}
}
