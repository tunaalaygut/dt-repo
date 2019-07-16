package com.alaygut.prototype.service;


import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.MeetingStatus;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddMeetingStatusForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingStatusRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MeetingStatusServiceImpl implements MeetingStatusService {
	
	private MeetingStatusRepository meetingStatusRepository;
	private ReasonService reasonService;
	private MemberService memberService;

	public MeetingStatusServiceImpl(MeetingStatusRepository meetingStatusRepository,ReasonService reasonService, MemberService memberService) {
		this.meetingStatusRepository = meetingStatusRepository;
		this.reasonService = reasonService;
		this.memberService = memberService;
	}

	@Override
	@Transactional(readOnly = false)
	public void addStatus(AddMeetingStatusForm form) {
		Reason reason = reasonService.getById(form);
		MeetingStatus meetingStatus = new MeetingStatus(
				form.getMeetingStatusName(),
				reason
		);
		meetingStatus.setCreator(memberService.getMember(form.getCreatorId()));
		meetingStatusRepository.save(meetingStatus);
	}

	@Override
	public Iterable<MeetingStatus> getAllStatus() {
		return meetingStatusRepository.findAll();
	}
	
	@Override
	public Iterable<MeetingStatus> getAllActiveStatus() {
		return meetingStatusRepository.findAllByStateEquals(RecordState.ACTIVE);
	}
	
	@Override
	public MeetingStatus getMeetingStatus(Long meetingStatusId) {
		return meetingStatusRepository.findById(meetingStatusId).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deactivate(IDTransfer idTransfer) {
		MeetingStatus meetingStatus = meetingStatusRepository.findById(idTransfer.getRecordId()).orElse(null);
		meetingStatus.setState(RecordState.NONACTIVE);
		meetingStatusRepository.save(meetingStatus);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void edit(AddMeetingStatusForm addMeetingStatusForm) {
		MeetingStatus meetingStatus = meetingStatusRepository.findById(addMeetingStatusForm.getRecordId()).orElse(null);
		Reason reason = reasonService.getById(addMeetingStatusForm);
		meetingStatus.setMeetingStatusName(addMeetingStatusForm.getMeetingStatusName());
		meetingStatus.setReason(reason);
		meetingStatus.setUpdater(memberService.getMember(addMeetingStatusForm.getUpdaterId()));

		meetingStatusRepository.save(meetingStatus)
;	}

	@Override
	public AddMeetingStatusForm getEditForm(Long meetingStatusId) {
		MeetingStatus meetingStatus = getMeetingStatus(meetingStatusId);
		AddMeetingStatusForm addMeetingStatusForm = new AddMeetingStatusForm();

		addMeetingStatusForm.setRecordId(meetingStatus.getMeetingStatusId());
		addMeetingStatusForm.setMeetingStatusName(meetingStatus.getMeetingStatusName());
		addMeetingStatusForm.setReasonId(meetingStatus.getReason().getReasonId());
		addMeetingStatusForm.setAllReasons(reasonService.getAllActiveReasons());

		return addMeetingStatusForm;
	}

	@Override
	public void fixForm(AddMeetingStatusForm form) {
		form.setAllReasons(reasonService.getAllActiveReasons());
	}

}
