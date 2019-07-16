package com.alaygut.prototype.domain;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

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