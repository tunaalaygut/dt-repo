package com.alaygut.prototype.dto;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.domain.RoomFeature;

import java.util.List;

public class AddMeetingRoomForm extends FormBase  {

	private String meetingRoomName;

	private Long buildingId;

	private Integer capacity;

	private List<Long> roomFeatureIds;

	private Iterable<Building> allBuildings;

	private Iterable<RoomFeature> allFeatures;

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

	public Iterable<RoomFeature> getAllFeatures() {
		return allFeatures;
	}

	public void setAllFeatures(Iterable<RoomFeature> allFeatures) {
		this.allFeatures = allFeatures;
	}

	public List<Long> getRoomFeatureIds() {
		return roomFeatureIds;
	}

	public void setRoomFeatureIds(List<Long> roomFeatureIds) {
		this.roomFeatureIds = roomFeatureIds;
	}
}
