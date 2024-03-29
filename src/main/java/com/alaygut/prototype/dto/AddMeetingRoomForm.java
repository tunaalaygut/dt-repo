package com.alaygut.prototype.dto;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.domain.RoomFeature;

import javax.validation.constraints.*;
import java.util.List;

public class AddMeetingRoomForm extends FormBase  {

	@Size(min = 2, max = 30, message = "{meetingRoomName.size.not.valid}")
	@Pattern(regexp ="[\\sa-zA-Z(İ,Ş,Ğ,Ç,Ö,Ü,ş,ğ,ö,ü,ç,ı\\d]*", message = "{meetingRoomName.not.valid}")
	private String meetingRoomName;

	@NotNull(message = "{buildingId.not.null}")
	private Long buildingId;

	@NotNull(message = "{capacity.not.null}")
	@Min(value = 1L, message = "{capacity.size.not.valid}")
	private Integer capacity;

	@NotEmpty(message = "{roomFeatureIds.not.empty}")
	private List<Long> roomFeatureIds;

	private Iterable<Building> allBuildings;

	private Iterable<RoomFeature> allFeatures;

	private Iterable<RoomFeature> meetingRoomFeatures;

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

	public Iterable<RoomFeature> getMeetingRoomFeatures() {
		return meetingRoomFeatures;
	}

	public void setMeetingRoomFeatures(Iterable<RoomFeature> meetingRoomFeatures) {
		this.meetingRoomFeatures = meetingRoomFeatures;
	}

	public boolean hasFeature(Long featureId){
		for (RoomFeature f: this.getMeetingRoomFeatures()) {
			if (f.getRoomFeatureId() == featureId)
				return true;
		}

		return false;
	}
}
