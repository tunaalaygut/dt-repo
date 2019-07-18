package com.alaygut.prototype.dto;

/**
 * Tüm DTO'ların kullandığı Base DTO, BaseClass domaininde saklanan bilgileri taşıyor
 * @author adincer
 *
 */
public class FormBase extends IDTransfer{
    private Long creatorId;
    private Long updaterId;

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }
}
