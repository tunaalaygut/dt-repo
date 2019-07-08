package com.alaygut.prototype.service;

import org.springframework.stereotype.Service; 
import com.alaygut.prototype.domain.MeetingType;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddMeetingTypeForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingTypeRepository;

@Service
public class MeetingTypeServiceImpl implements MeetingTypeService {
	
	private MeetingTypeRepository meetingTypeRepository;

	public MeetingTypeServiceImpl(MeetingTypeRepository meetingTypeRepository) {
		this.meetingTypeRepository = meetingTypeRepository;
	}
	
	@Override
	public void addType(AddMeetingTypeForm form) {
		MeetingType meetingType = new MeetingType(
				form.getMeetingTypeName(),
				form.getDescription()
		);
		meetingTypeRepository.save(meetingType);
	}

	@Override
	public Iterable<MeetingType> getAllTypes() {
		return meetingTypeRepository.findAll();
	}
	
	@Override
	public Iterable<MeetingType> getAllActiveTypes() {
		return meetingTypeRepository.findAllByStateEquals(RecordState.ACTIVE);
	}
	
	@Override
	public MeetingType getMeetingType(Long meetingTypeId) {
		return meetingTypeRepository.findById(meetingTypeId).orElse(null);
	}
	
	@Override
	public void deactivate(IDTransfer idTransfer) {
		MeetingType meetingType = meetingTypeRepository.findById(idTransfer.getRecordId()).orElse(null);
		meetingType.setState(RecordState.NONACTIVE);
		meetingTypeRepository.save(meetingType);
	}
	
	@Override
	public void edit(AddMeetingTypeForm addMeetingTypeForm) {
		MeetingType meetingType = meetingTypeRepository.findById(addMeetingTypeForm.getRecordId()).orElse(null);
		meetingType.setMeetingTypeName(addMeetingTypeForm.getMeetingTypeName());
		meetingType.setDescription(addMeetingTypeForm.getDescription());
		meetingTypeRepository.save(meetingType);	
	}

}
