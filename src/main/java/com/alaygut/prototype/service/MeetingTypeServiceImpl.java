package com.alaygut.prototype.service;
 
import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.MeetingType;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddMeetingTypeForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingTypeRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MeetingTypeServiceImpl implements MeetingTypeService {
	
	private MeetingTypeRepository meetingTypeRepository;
	private MemberService memberService;

	public MeetingTypeServiceImpl(MeetingTypeRepository meetingTypeRepository, MemberService memberService) {
		this.meetingTypeRepository = meetingTypeRepository;
		this.memberService = memberService;
	}

	/**
	 * Database'e toplantı türü ekleme
	 * @param form building DTO
	 */

	@Override
	@Transactional(readOnly = false)
	public void addType(AddMeetingTypeForm form) {
		MeetingType meetingType = new MeetingType(
				form.getMeetingTypeName(),
				form.getDescription()
		);
		meetingType.setCreator(memberService.getMember(form.getCreatorId()));
		meetingTypeRepository.save(meetingType);
	}

	@Override
	public Iterable<MeetingType> getAllTypes() {
		return meetingTypeRepository.findAll();
	}
	
	/**
	 * Stateleri Aktif(1) olan toplantı türlerini döndürür
	 */

	@Override
	public Iterable<MeetingType> getAllActiveTypes() {
		return meetingTypeRepository.findAllByStateEquals(RecordState.ACTIVE);
	}
	
	/**
	 * Spesifik bir toplantı türü döndürür
	 * @param meetingTypeId toplantı türünün unique id'si    
	 */

	@Override
	public MeetingType getMeetingType(Long meetingTypeId) {
		return meetingTypeRepository.findById(meetingTypeId).orElse(null);
	}
	
	/**
	 * State'i Aktiften(1) Deaktife(0) alır
	 * @param idTransfer id transfer objesi
	 */

	@Override
	@Transactional(readOnly = false)
	public void deactivate(IDTransfer idTransfer) {
		MeetingType meetingType = meetingTypeRepository.findById(idTransfer.getRecordId()).orElse(null);
		meetingType.setState(RecordState.NONACTIVE);
		meetingTypeRepository.save(meetingType);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void edit(AddMeetingTypeForm addMeetingTypeForm) {
		MeetingType meetingType = meetingTypeRepository.findById(addMeetingTypeForm.getRecordId()).orElse(null);
		meetingType.setMeetingTypeName(addMeetingTypeForm.getMeetingTypeName());
		meetingType.setDescription(addMeetingTypeForm.getDescription());
		meetingType.setUpdater(memberService.getMember(addMeetingTypeForm.getUpdaterId()));

		meetingTypeRepository.save(meetingType);	
	}

	/**
	 * Editlenecek toplantı türünün formunu dolu halde getirir
	 * @param meetingTypeId editlenecek toplantı türünün Id'si
	 * @return dolu meetingType DTO'su
	 */

	@Override
	public AddMeetingTypeForm getEditForm(Long meetingTypeId) {
		MeetingType meetingType = getMeetingType(meetingTypeId);
		AddMeetingTypeForm addMeetingTypeForm = new AddMeetingTypeForm();

		addMeetingTypeForm.setRecordId(meetingType.getMeetingTypeId());
		addMeetingTypeForm.setMeetingTypeName(meetingType.getMeetingTypeName());
		addMeetingTypeForm.setDescription(meetingType.getDescription());

		return addMeetingTypeForm;
	}

}
