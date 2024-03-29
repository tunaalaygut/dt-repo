package com.alaygut.prototype.domain;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * BaseClass butun entitylerin paylastigi ortak ozellikleri tutar
 * Creation,update timestampleri, creator ve updaterlar, enum state gibi
 * @author adincer
 *
 */
@MappedSuperclass
public class BaseClass {

	@CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

	@UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

    @ManyToOne(optional = true)
    @JoinColumn(name = "creator")
    private Member creator;

    @ManyToOne(optional = true)
    @JoinColumn(name = "updater")
    private Member updater;

    @Enumerated(EnumType.ORDINAL)
    private RecordState state;

    public BaseClass() {    //default constructor
        this.setState(RecordState.ACTIVE);
        //current user
    }

    public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public Member getCreator() {
        return creator;
    }

    public void setCreator(Member creator) {
        this.creator = creator;
    }

    public Member getUpdater() {
        return updater;
    }

    public void setUpdater(Member updater) {
        this.updater = updater;
    }

    public RecordState getState() {
        return state;
    }

    public void setState(RecordState state) {
        this.state = state;
    }
}