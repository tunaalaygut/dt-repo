package com.alaygut.prototype.dto;

/**
 * Id için transfer objesi, Base DTO tarafından extendleniyor
 * @author adincer
 *
 */
public class IDTransfer {

    private Long recordId;
    
    public IDTransfer() {
	}

	public IDTransfer(Long recordId) {
		this.recordId = recordId;
	}

	public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
