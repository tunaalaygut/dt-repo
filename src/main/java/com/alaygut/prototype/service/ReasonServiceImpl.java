package com.alaygut.prototype.service;


import com.alaygut.prototype.dto.AddMeetingStatusForm;
import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddReasonForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.ReasonRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ReasonServiceImpl implements ReasonService {
	
	private ReasonRepository reasonRepository;
	private MemberService memberService;

	public ReasonServiceImpl(ReasonRepository reasonRepository, MemberService memberService) {
		this.reasonRepository = reasonRepository;
		this.memberService = memberService;
	}

	/**
	 * Database'e sebep ekleme
	 * @param addReasonForm reason DTO
	 */

	@Override
	@Transactional(readOnly = false)
	public void addReason(AddReasonForm addReasonForm) {
		Reason reason = new Reason(
				addReasonForm.getReasonName(),
				addReasonForm.getDescription()
		);
		reason.setCreator(memberService.getMember(addReasonForm.getCreatorId()));
		reasonRepository.save(reason);
	}

	@Override
	public Iterable<Reason> getAllReasons() {
		return reasonRepository.findAll();
	}
	
	/**
	 * Stateleri Aktif(1) olan sebepleri döndürür
	 */

	@Override
	public Iterable<Reason> getAllActiveReasons() {
		return reasonRepository.findAllByStateEquals(RecordState.ACTIVE);
	}
	
	/**
	 * Spesifik bir sebep döndürür
	 * @param reasonId sebebin unique id'si    
	 */

	@Override
	public Reason getReason(Long reasonId) {
		return reasonRepository.findById(reasonId).orElse(null);
	}
	
	/**
	 * State'i Aktiften(1) Deaktife(0) alır
	 * @param idTransfer id transfer objesi
	 */

	@Override
	@Transactional(readOnly = false)
	public void deactivate(IDTransfer idTransfer) {
		Reason reason = reasonRepository.findById(idTransfer.getRecordId()).orElse(null);
		reason.setState(RecordState.NONACTIVE);
		reasonRepository.save(reason);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void edit(AddReasonForm addReasonForm) {
		Reason reason = reasonRepository.findById(addReasonForm.getRecordId()).orElse(null);
		reason.setReasonName(addReasonForm.getReasonName());
		reason.setDescription(addReasonForm.getDescription());
		reason.setUpdater(memberService.getMember(addReasonForm.getUpdaterId()));

			reasonRepository.save(reason);
	}

	/**
	 * Editlenecek sebebin formunu dolu halde getirir
	 * @param reasonId editlenen sebebin Id'si
	 * @return dolu reason DTO'su
	 */

	@Override
	public AddReasonForm getEditForm(Long reasonId) {
		Reason reason = getReason(reasonId);
		AddReasonForm addReasonForm = new AddReasonForm();

		addReasonForm.setRecordId(reason.getReasonId());
		addReasonForm.setReasonName(reason.getReasonName());
		addReasonForm.setDescription(reason.getDescription());

		return addReasonForm;
	}

	@Override
	public Reason getById(AddMeetingStatusForm addMeetingStatusForm) {
		return reasonRepository.findById(addMeetingStatusForm.getReasonId()).orElse(null);
	}



}
