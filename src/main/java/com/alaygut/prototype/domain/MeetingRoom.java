package com.alaygut.prototype.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class MeetingRoom extends BaseClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meetingRoomId", nullable = false, updatable = false)
	private Long meetingRoomId;

	@Column(name = "meetingRoomName")
	private String meetingRoomName;

	@ManyToOne(optional = true)
	@JoinColumn(name = "buildingId")
	private Building building;

	@ManyToMany(targetEntity = RoomFeature.class, fetch = FetchType.EAGER)
	@JoinTable(name = "Meeting_Room_Feature",
			joinColumns = @JoinColumn(name = "meetingRoomId"),
			inverseJoinColumns = @JoinColumn(name = "roomFeatureId"))
	private Set roomFeatureSet;

	@Column(name = "capacity")
	private Integer capacity;

	public MeetingRoom() {
	} //default constructor

	public MeetingRoom(String meetingRoomName, Building building, int capacity, Set roomFeatureSet) {
		this.meetingRoomName = meetingRoomName;
		this.building = building;
		this.capacity = capacity;
		this.roomFeatureSet = roomFeatureSet;

	}

	public String getAllRoomFeaturesString(){
		String result = "";

		for (RoomFeature roomFeature: this.getRoomFeatureSet())
			result += (roomFeature.getFeatureName() + ", ");

		return result.replaceAll(", $", "");    //to remove the last comma from the string.
	}

	public List<String> getAllFeatureNames(){
		List<String> featureNames = new ArrayList<>();

		for (RoomFeature roomFeature : this.getRoomFeatureSet())
			featureNames.add(roomFeature.getFeatureName());

		return featureNames;
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

	public Set<RoomFeature> getRoomFeatureSet() {
		return roomFeatureSet;
	}

	public void setRoomFeatureSet(Set roomFeatureSet) {
		this.roomFeatureSet = roomFeatureSet;
	}
}
