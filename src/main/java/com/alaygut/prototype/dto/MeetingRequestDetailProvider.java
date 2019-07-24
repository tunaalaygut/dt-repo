package com.alaygut.prototype.dto;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.Participant;

import java.util.List;
import java.util.Map;

public class MeetingRequestDetailProvider {
    private Map<MeetingRequest, List<Participant>> meetingParticipants;

    public Map<MeetingRequest, List<Participant>> getMeetingParticipants() {
        return meetingParticipants;
    }

    public void setMeetingParticipants(Map<MeetingRequest, List<Participant>> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
    }

}
