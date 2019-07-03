package com.alaygut.prototype.domain;

import javax.persistence.*;

@Entity
public class MeetingType extends BaseClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meetingTypeId", nullable = false, updatable = false)
	private Long meetingTypeId;

	@Column(name = "meetingTypeName")
	private String meetingTypeName;

	@Column(name = "description")
	private String description;

	public MeetingType() {
	}

	public MeetingType(String meetingTypeName, String description) {
		this.meetingTypeName = meetingTypeName;
		this.description = description;
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

	public String getMeetingTypeName() {
		return meetingTypeName;
	}

	public void setMeetingTypeName(String meetingTypeName) {
		this.meetingTypeName = meetingTypeName;
	}
}
