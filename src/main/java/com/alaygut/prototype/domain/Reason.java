package com.alaygut.prototype.domain;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Reason extends BaseClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reasonId", nullable = false, updatable = false)
	private Long reasonId;
	
	@Column(name = "reasonName")
	private String reasonName;
	
	@Column(name = "description")
	private String description;

	
	public Reason() {
		//default constructor
	}
	
	
	public Reason(String reasonName, String description) {
		this.reasonName = reasonName;
		this.description = description;
	}


	public Reason(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	
	
	
}
