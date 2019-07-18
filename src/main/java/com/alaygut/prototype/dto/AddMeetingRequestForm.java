package com.alaygut.prototype.dto;

<<<<<<< HEAD
 




import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

=======
import java.util.*;
>>>>>>> 88a5f7e0967aa42ebeb02dd4a9dd65cb1bed5cf0
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alaygut.prototype.domain.*;
import org.springframework.format.annotation.DateTimeFormat;

public class AddMeetingRequestForm extends FormBase  {
	
	//@NotNull(message = "{meetingRoomId.not.null")
	private Long meetingRoomId;
	
	//@NotNull(message = "{memberId.not.null}")
	private Long memberId;
	
	@NotNull(message = "{meetingTypeId.not.null}")
	private Long meetingTypeId;

	private Long buildingId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotEmpty(message = "{date.not.empty}")
	private String date;

	@NotEmpty(message = "{beginningTime.not.empty}")
	private String beginningTime;

	@NotEmpty(message = "{endTime.not.empty}")
	private String endTime;
	
	@Size(max = 250)
	private String description;

	private Iterable<Building> allBuildings;
	
	private Iterable<Member> allMembers;

	private List<Long> addedMemberIds;

	private Iterable<MeetingType> allMeetingTypes;

	private Iterable<MeetingRoom> allMeetingRooms;

	private List<String> participantDetails;

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

	public List<Long> getAddedMemberIds() {
		return addedMemberIds;
	}

	public void setAddedMemberIds(List<Long> addedMemberIds) {
		this.addedMemberIds = addedMemberIds;
	}

	public List<String> getParticipantDetails() {
		return participantDetails;
	}

	public void setParticipantDetails(List<String> participantDetails) {
		this.participantDetails = participantDetails;
	}
}
