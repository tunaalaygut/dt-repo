package com.alaygut.prototype.service;

import com.alaygut.prototype.dto.MeetingDetailProvider;

import java.security.Principal;
import java.util.List;

public interface MeetingService {
    List<MeetingDetailProvider> getMemberMeetingDetailsProvider(Principal principal);
}
