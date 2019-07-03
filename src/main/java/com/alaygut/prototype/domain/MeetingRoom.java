package com.alaygut.prototype.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
public class MeetingRoom extends BaseClass{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meetingRoomId", nullable = false, updatable = false)
	private Long meetingRoomId;

	@Column(name = "meetingRoomName")
	private String meetingRoomName;

	@ManyToOne(optional = true)
	@JoinColumn(name = "buildingId")
	private Building building;
	
	@Column(name = "capacity")
	private Integer capacity;
	
	public MeetingRoom () {} //default constructor
	
	public MeetingRoom(String meetingRoomName, Building building, int capacity) {
		this.meetingRoomName = meetingRoomName;
		this.building = building;
		this.capacity = capacity;
	}

	public Long getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(Long meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getMeetingRoomName() {
		return meetingRoomName;
	}

	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
	}
}
