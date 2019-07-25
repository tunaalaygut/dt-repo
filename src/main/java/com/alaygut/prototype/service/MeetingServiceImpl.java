package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.MeetingState;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.Participant;
import com.alaygut.prototype.dto.MeetingDetailProvider;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    ParticipantService participantService;
    MemberService memberService;

    public MeetingServiceImpl(ParticipantService participantService, MemberService memberService) {
        this.participantService = participantService;
        this.memberService = memberService;
    }

    @Override
    public List<MeetingDetailProvider> getMemberMeetingDetailsProvider(Principal principal) {
        List<MeetingDetailProvider> providers = new ArrayList<>();

        Member member = memberService.getMember(principal.getName());
        List<Participant> participants = participantService.getAllByMember(member);

        for (Participant participant : participants){
            MeetingRequest request = participant.getMeetingRequest();
            if (request.getMeetingRequestState().equals(MeetingState.ONAYLANDI)){
                MeetingDetailProvider provider = new MeetingDetailProvider();
                populateProvider(provider, request);
                providers.add(provider);
            }
        }

        return providers;
    }

    private void populateProvider(MeetingDetailProvider provider, MeetingRequest request){
        provider.setDate(request.getDate());
        provider.setStartTime(request.getStartTime());
        provider.setEndTime(request.getEndTime());
        provider.setMember(request.getMember());
        provider.setBuilding(request.getMeetingRoom().getBuilding());
        provider.setMeetingRoom(request.getMeetingRoom());
        provider.setMeetingType(request.getMeetingType());
        provider.setDescription(request.getDescription());
        provider.setParticipants(participantService.getAllParticipantsInMeetingRequest(request));
    }


}
