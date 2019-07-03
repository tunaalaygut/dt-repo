package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingType;
import com.alaygut.prototype.dto.AddMeetingTypeForm;

public interface MeetingTypeService {
	void addType(AddMeetingTypeForm addMeetingTypeForm);
	Iterable<MeetingType> getAllTypes();
	Iterable<MeetingType> getAllActiveTypes();
}
