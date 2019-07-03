package com.alaygut.prototype.service;

import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.dto.AddReasonForm;
import com.alaygut.prototype.repository.ReasonRepository;

@Service
public class ReasonServiceImpl implements ReasonService {
	
	private ReasonRepository reasonRepository;

	public ReasonServiceImpl(ReasonRepository reasonRepository) {
		this.reasonRepository = reasonRepository;
	}
	
	@Override
	public void addReason(AddReasonForm addReasonForm) {
		Reason reason = new Reason(
				addReasonForm.getReasonName(),
				addReasonForm.getDescription()
		);
		reasonRepository.save(reason);
	}

	@Override
	public Iterable<Reason> getAllReasons() {
		return reasonRepository.findAll();
	}
}
