package com.alaygut.prototype.domain;

import javax.persistence.*; 
import com.alaygut.prototype.domain.Member;

@Entity
public class Participant extends BaseClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "participationId", nullable = false, updatable = false)
	private Long participationId;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "memberId")
	private Member member;
	
	//@Column(name = "meetingRequestId", nullable = false, updatable = false)
	  //private MeetingRequest meetingRequest;
	
	@Column(name = "firstName", nullable = false) 
	private String firstName;
	
	@Column(name = "lastName", nullable = false) 
	private String lastName;
	
	@Column(name = "email", nullable = false) 
	private String email;
	
	public Participant() {} //default constructor
	
	public Participant(Member member/*,MeetingRequest meetingRequest*/) {
		this.member = member;
	    //this.meetingRequest = meetingRequest;
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
	
	
}
