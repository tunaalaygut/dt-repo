package com.alaygut.prototype.dto;

import javax.validation.constraints.Size;

public class AddReasonForm extends FormBase{
	
	@Size(min = 3, max = 30, message = "Sebep adı [3-30] karakter uzunluğunda olmalıdır.")
	private String reasonName;
	
	@Size(min = 0, max = 400)
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

}
