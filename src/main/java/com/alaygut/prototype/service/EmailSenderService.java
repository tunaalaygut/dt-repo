package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRequest;
import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
    void sendEmail(SimpleMailMessage email);
    void sendRejectionEmail(MeetingRequest meetingRequest);
    void sendConfirmationEmail(MeetingRequest meetingRequest);
    void sendCancelEmail(MeetingRequest meetingRequest);
    void sendNotificationEmail(MeetingRequest meetingRequest);
}
