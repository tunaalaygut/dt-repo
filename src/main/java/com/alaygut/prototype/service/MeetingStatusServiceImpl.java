package com.alaygut.prototype.service;

import java.util.Optional;

import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.MeetingStatus;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddMeetingStatusForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingStatusRepository;
import com.alaygut.prototype.repository.ReasonRepository;

@Service
public class MeetingStatusServiceImpl implements MeetingStatusService {
	
	private MeetingStatusRepository meetingStatusRepository;
	private ReasonRepository reasonRepository;
	private MemberRepository memberRepository;
	private ReasonService reasonService;

	public MeetingStatusServiceImpl(MeetingStatusRepository meetingStatusRepository, ReasonRepository reasonRepository, MemberRepository memberRepository, ReasonService reasonService) {
		this.meetingStatusRepository = meetingStatusRepository;
		this.reasonRepository = reasonRepository;
		this.memberRepository = memberRepository;
		this.reasonService = reasonService;
	}

	@Override
	public void addStatus(AddMeetingStatusForm form) {
		Optional<Reason> reason = reasonRepository.findById(form.getReasonId());
		MeetingStatus meetingStatus = new MeetingStatus(
				form.getMeetingStatusName(),
				reason.orElse(null)
		);
		meetingStatus.setCreator(memberRepository.findById(form.getCreatorId()).orElse(null));
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
	public void deactivate(IDTransfer idTransfer) {
		MeetingStatus meetingStatus = meetingStatusRepository.findById(idTransfer.getRecordId()).orElse(null);
		meetingStatus.setState(RecordState.NONACTIVE);
		meetingStatusRepository.save(meetingStatus);
	}
	
	@Override
	public void edit(AddMeetingStatusForm addMeetingStatusForm) {
		MeetingStatus meetingStatus = meetingStatusRepository.findById(addMeetingStatusForm.getRecordId()).orElse(null);
		Reason reason = reasonRepository.findById(addMeetingStatusForm.getReasonId()).orElse(null);
		meetingStatus.setMeetingStatusName(addMeetingStatusForm.getMeetingStatusName());
		meetingStatus.setReason(reason);
		meetingStatus.setUpdater(memberRepository.findById(addMeetingStatusForm.getUpdaterId()).orElse(null));

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
}
