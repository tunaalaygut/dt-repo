package com.alaygut.prototype.domain;


import java.time.LocalTime;
import javax.persistence.*;

@Entity
public class MeetingRequest extends BaseClass{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meetingRequestId", nullable = false, updatable = false)
	private Long meetingRequestId;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "meetinRoomId")
	private MeetingRoom meetingRoom;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "memberId")
	private Member member;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "meetingTypeId")
	private MeetingType meetingType;
	
	@Column(name = "startTime")
	private LocalTime startTime;
	
	@Column(name = "endTime")
	private LocalTime endTime;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "meetingStatusId")
	private MeetingStatus meetingStatus;
	
	public MeetingRequest() {}; //default constructor
	
	public MeetingRequest(MeetingRoom meetingRoom, Member member, MeetingType meetingType,
			LocalTime startTime,LocalTime endTime, String description,  MeetingStatus meetingStatus) {
		this.meetingRoom = meetingRoom;
		this.member = member;
		this.meetingType = meetingType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.meetingStatus = meetingStatus;
		
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

	public MeetingStatus getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(MeetingStatus meetingStatus) {
		this.meetingStatus = meetingStatus;
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
