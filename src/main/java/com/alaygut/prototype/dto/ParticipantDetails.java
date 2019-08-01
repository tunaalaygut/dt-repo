package com.alaygut.prototype.dto;


import com.alaygut.prototype.domain.ParticipantType;

public class ParticipantDetails {
    private Long memberId;  //This is optional. Only members will have this field filled. Guests will have it null.
    private String fullName;
    private String email;
    private ParticipantType participantType;

    public ParticipantDetails() { } //default constructor

    public ParticipantDetails(Long memberId, String fullName, String email, ParticipantType participantType) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.email = email;
        this.participantType = participantType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }
}
