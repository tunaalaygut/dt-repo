package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingType;
import com.alaygut.prototype.dto.AddMeetingTypeForm;
import com.alaygut.prototype.dto.IDTransfer;

public interface MeetingTypeService {
	void addType(AddMeetingTypeForm addMeetingTypeForm);
	Iterable<MeetingType> getAllTypes();
	Iterable<MeetingType> getAllActiveTypes();
	void deactivate(IDTransfer idTransfer);
}
