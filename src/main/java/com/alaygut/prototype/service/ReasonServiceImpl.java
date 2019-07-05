package com.alaygut.prototype.service;

import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddReasonForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.ReasonRepository;

@Service
public class ReasonServiceImpl implements ReasonService {
	
	private ReasonRepository reasonRepository;
	private MemberRepository memberRepository;

	public ReasonServiceImpl(ReasonRepository reasonRepository, MemberRepository memberRepository) {
		this.reasonRepository = reasonRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public void addReason(AddReasonForm addReasonForm) {
		Reason reason = new Reason(
				addReasonForm.getReasonName(),
				addReasonForm.getDescription()
		);
		reason.setCreator(memberRepository.findById(addReasonForm.getCreatorId()).orElse(null));
		reasonRepository.save(reason);
	}

	@Override
	public Iterable<Reason> getAllReasons() {
		return reasonRepository.findAll();
	}
	
	@Override
	public Iterable<Reason> getAllActiveReasons() {
		return reasonRepository.findAllByStateEquals(RecordState.ACTIVE);
	}
	
	@Override
	public Reason getReason(Long reasonId) {
		return reasonRepository.findById(reasonId).orElse(null);
	}
	
	@Override
	public void deactivate(IDTransfer idTransfer) {
		Reason reason = reasonRepository.findById(idTransfer.getRecordId()).orElse(null);
		reason.setState(RecordState.NONACTIVE);
		reasonRepository.save(reason);
	}
	
	@Override
	public void edit(AddReasonForm addReasonForm) {
		Reason reason = reasonRepository.findById(addReasonForm.getRecordId()).orElse(null);
		reason.setReasonName(addReasonForm.getReasonName());
		reason.setDescription(addReasonForm.getDescription());
		reasonRepository.save(reason);
	}
}
