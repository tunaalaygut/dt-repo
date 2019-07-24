package com.alaygut.prototype.dto;

/**
 * Onaylanan toplantıların talep ekranında görüntülenecek bilgilerini transfer eden obje.
 * supervisorFullName, toplantı talebini onaylayan supervisor'ın adı ve soyadı.
 * beginningTime, toplantının string olarak ("hh:MM") formatında başlangıç saati.
 * endTime, toplantının string olarak ("hh:MM") formatında bitiş saati.
 * participants, katılımcıların isimlerinin virgül ile ayrılarak yazıldığı bir string.
 */

public class MeetingDetail {
    Long meetingRequestId;
    String supervisorFullName;
    String beginningTime;
    String endTime;
    String member;
    String memberId;
    String participants;
    String meetingType;

    public Long getMeetingRequestId() {
        return meetingRequestId;
    }

    public void setMeetingRequestId(Long meetingRequestId) {
        this.meetingRequestId = meetingRequestId;
    }

    public String getSupervisorFullName() {
        return supervisorFullName;
    }

    public void setSupervisorFullName(String supervisorFullName) {
        this.supervisorFullName = supervisorFullName;
    }

    public String getBeginningTime() {
        return beginningTime;
    }

    public void setBeginningTime(String beginningTime) {
        this.beginningTime = beginningTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }
}


