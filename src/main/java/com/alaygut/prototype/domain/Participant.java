package com.alaygut.prototype.domain;
  
import javax.persistence.*;

import com.alaygut.prototype.domain.Member;

@Entity
public class Participant extends BaseClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "participationId", nullable = false, updatable = false)
	private Long participationId;
	
	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;
	
	@Column(name = "fullName", nullable = false)
	private String fullName;
	
	@Column(name = "email", nullable = false) 
	private String email;

	@Column(name = "participantType", nullable = false)
	@Enumerated
	private ParticipantType participantType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "meetingRequestId")
	private MeetingRequest meetingRequest;
	
	public Participant() {} //default constructor

	public Participant(Member member, String fullName, String email, MeetingRequest meetingRequest, ParticipantType participantType) {
		this.member = member;
		this.fullName = fullName;
		this.email = email;
		this.meetingRequest = meetingRequest;
		this.participantType = participantType;
	}

	public Long getParticipationId() {
		return participationId;
	}

	public void setParticipationId(Long participationId) {
		this.participationId = participationId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
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

	public MeetingRequest getMeetingRequest() {
		return meetingRequest;
	}

	public void setMeetingRequest(MeetingRequest meetingRequest) {
		this.meetingRequest = meetingRequest;
	}

	public ParticipantType getParticipantType() { return participantType; }

	public void setParticipantType(ParticipantType participantType) { this.participantType = participantType; }
}
