package com.alaygut.prototype.domain;

import javax.persistence.*; 
import java.time.LocalDate;

@MappedSuperclass
public class BaseClass {

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "updated")
    private LocalDate updated;

    @ManyToOne(optional = true)
    @JoinColumn(name = "creator")
    private Member creator;

    @ManyToOne(optional = true)
    @JoinColumn(name = "updater")
    private Member updater;

    @Enumerated(EnumType.ORDINAL)
    private RecordState state;

    public BaseClass() {    //default constructor
        this.setCreated(LocalDate.now());
        this.setState(RecordState.ACTIVE);
        //current user
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
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
