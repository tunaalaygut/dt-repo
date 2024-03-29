package com.alaygut.prototype.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddMeetingTypeForm extends FormBase  {

	@Size(min = 5, max = 50, message ="{meetingTypeName.size.not.valid}")
	private String meetingTypeName;

	@Size(max = 200)
	private String description;

	public String getMeetingTypeName() {
		return meetingTypeName;
	}

	public void setMeetingTypeName(String meetingTypeName) {
		this.meetingTypeName = meetingTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
