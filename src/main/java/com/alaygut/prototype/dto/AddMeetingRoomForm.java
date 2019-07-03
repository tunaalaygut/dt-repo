package com.alaygut.prototype.dto;

import com.alaygut.prototype.domain.Building;

public class AddMeetingRoomForm {
	
	private String meetingRoomName;

	private Long buildingId;

	private Integer capacity;

	private Iterable<Building> allBuildings;

	public String getMeetingRoomName() {
		return meetingRoomName;
	}

	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Iterable<Building> getAllBuildings() {
		return allBuildings;
	}

	public void setAllBuildings(Iterable<Building> allBuildings) {
		this.allBuildings = allBuildings;
	}
}
