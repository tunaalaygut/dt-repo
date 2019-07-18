package com.alaygut.prototype.dto;

 




import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alaygut.prototype.domain.*;
import org.springframework.format.annotation.DateTimeFormat;

public class AddMeetingRequestForm extends FormBase  {
	
	//@NotNull(message = "Toplantı isteği için bir oda seçilmelidir.")
	private Long meetingRoomId;
	
	//@NotNull(message = "Toplantı isteği bir üye tarafından ayarlanmalıdır.")
	private Long memberId;
	
	@NotNull(message = "Toplantı isteği bir türe sahip olmalıdır.")
	private Long meetingTypeId;

	private Long buildingId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotEmpty(message = "Tarih seçilmedi.")
	private String date;

	@NotEmpty(message = "Başlangıç zamanı seçilmedi.")
	private String beginningTime;

	@NotEmpty(message = "Bitiş zamanı seçilmedi.")
	private String endTime;
	
	@Size(max = 250)
	private String description;

	private Iterable<Building> allBuildings;
	
	private Iterable<Member> allMembers;

	private ArrayList<Long> addedMembers;

	private Iterable<MeetingType> allMeetingTypes;

	private Iterable<MeetingRoom> allMeetingRooms;

	private ArrayList<String> times;

	private Map<String, Set<String>> buildingMeetingRooms;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBeginningTime() {
		return beginningTime;
	}

	public void setBeginningTime(String beginningTime) {
		this.beginningTime = beginningTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Iterable<Member> getAllMembers() {
		return allMembers;
	}

	public void setAllMembers(Iterable<Member> allMembers) {
		this.allMembers = allMembers;
	}

	public Iterable<MeetingType> getAllMeetingTypes() {
		return allMeetingTypes;
	}

	public void setAllMeetingTypes(Iterable<MeetingType> allMeetingTypes) {
		this.allMeetingTypes = allMeetingTypes;
	}

	public ArrayList<String> getTimes() {
		return times;
	}

	public void setTimes(ArrayList<String> times) {
		this.times = times;
	}

	public Iterable<Building> getAllBuildings() {
		return allBuildings;
	}

	public void setAllBuildings(Iterable<Building> allBuildings) {
		this.allBuildings = allBuildings;
	}

	public Iterable<MeetingRoom> getAllMeetingRooms() {
		return allMeetingRooms;
	}

	public void setAllMeetingRooms(Iterable<MeetingRoom> allMeetingRooms) {
		this.allMeetingRooms = allMeetingRooms;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public ArrayList<Long> getAddedMembers() {
		return addedMembers;
	}

	public void setAddedMembers(ArrayList<Long> addedMembers) {
		this.addedMembers = addedMembers;
	}
}
