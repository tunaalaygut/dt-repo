package com.alaygut.prototype.service;
 
import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.Participant;
import com.alaygut.prototype.domain.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private JavaMailSender javaMailSender;
    private ParticipantService participantService;
    private RoleService roleService;
    private MemberService memberService;


    @Value("{program.url}")
    private String PROGRAM_URL;

    public EmailSenderServiceImpl(JavaMailSender javaMailSender, ParticipantService participantService, RoleService roleService, MemberService memberService) {
        this.javaMailSender = javaMailSender;
        this.participantService = participantService;
        this.roleService = roleService;
        this.memberService = memberService;
    }

    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    private String[] getRecipients(List<Participant> participants) {
        int size = participants.size();

        InternetAddress[] address = new InternetAddress[size];
        String[] recipients = new String[size];

        for (int i = 0; i < size; i++) {
            try {
                address[i] = new InternetAddress(participants.get(i).getEmail());
                recipients[i] = address[i].toString();
            } catch (AddressException e) {
                e.printStackTrace();
            }
        }

        return recipients;
    }

    @Override
    @Async
    public void sendCancelEmail(MeetingRequest meetingRequest) {
        List<Participant> participants = participantService.getAllParticipantsInMeetingRequest(meetingRequest);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Toplantı İptali Hakkında Bilgilendirme");
        mailMessage.setFrom("dijital.toplanti@gmail.com");
        mailMessage.setText("Aşağıda özellikleri belirtilen toplantı iptal edilmiştir.\nToplantının Bilgileri: " +
                "\nTarih: " + meetingRequest.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\nSaat: " + meetingRequest.getStartTime() + " - " + meetingRequest.getEndTime() +
                "\nBina: " + meetingRequest.getMeetingRoom().getBuilding().getBuildingName() + "\nOda: " + meetingRequest.getMeetingRoom().getMeetingRoomName() +
                "\nToplantı Türü: " + meetingRequest.getMeetingType().getMeetingTypeName() +
                "\nToplantı Açıklaması: " + meetingRequest.getDescription());

        mailMessage.setTo(getRecipients(participants));
        sendEmail(mailMessage);
    }


    @Override
    @Async
    public void sendConfirmationEmail(MeetingRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(request.getCreator().getEmail());
        mailMessage.setSubject("Toplantı Talebiniz Hakkında Bilgilendirme");
        mailMessage.setFrom("dijital.toplanti@gmail.com");
        mailMessage.setText("Toplantı talebiniz kabul edilmiştir.\nToplantı Bilgileriniz: " +
                "\nTarih: " + request.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\nSaat: " + request.getStartTime() + " - " + request.getEndTime() +
                "\nBina: " + request.getMeetingRoom().getBuilding().getBuildingName() + "\nOda: " + request.getMeetingRoom().getMeetingRoomName() +
                "\nToplantı Türü: " + request.getMeetingType().getMeetingTypeName() +
                "\nToplantı Açıklaması: " + request.getDescription() +
                "\n\nAşağıdaki linkten Dijital Toplantı'ya ulaşabilirsiniz:\n" + PROGRAM_URL);

        // Sends the email to the user who requested the meeting
        sendEmail(mailMessage);


        // Sends the email to all participants of the meeting
        List<Participant> participants = participantService.getAllParticipantsInMeetingRequest(request);
        InternetAddress[] Address = new InternetAddress[participants.size()];
        SimpleMailMessage mailMessage1 = new SimpleMailMessage();
        mailMessage1.setSubject("Toplantı Hakkında Bilgilendirme");
        mailMessage1.setFrom("dijital.toplanti@gmail.com");
        mailMessage1.setText(request.getCreator().getFirstName() + " " + request.getCreator().getLastName() +
                " sizi aşağıda bilgileri belirtilen toplantıya eklemiştir.\n\nToplantının Bilgileri: " +
                "\nTarih: " + request.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\nSaat: " + request.getStartTime() + " - " + request.getEndTime() +
                "\nBina: " + request.getMeetingRoom().getBuilding().getBuildingName() + "\nOda: " + request.getMeetingRoom().getMeetingRoomName() +
                "\nToplantı Türü: " + request.getMeetingType().getMeetingTypeName() +
                "\nToplantı Açıklaması: " + request.getDescription() +
                "\n\nAşağıdaki linkten Dijital Toplantı'ya ulaşabilirsiniz:\n " + PROGRAM_URL);


        for (int i = 0; i < participants.size(); i++) {
            try {
                Address[i] = new InternetAddress(participants.get(i).getEmail());
            } catch (AddressException e) {
                e.printStackTrace();
            }
            if (!request.getCreator().getEmail().equals(Address[i].toString())) {
                mailMessage1.setTo(Address[i].toString());
                sendEmail(mailMessage1);
            }
        }

    }

    @Override
    @Async
    public void sendRejectionEmail(MeetingRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(request.getCreator().getEmail());
        mailMessage.setSubject("Toplantı Talebiniz Hakkında Bilgilendirme");
        mailMessage.setFrom("dijital.toplanti@gmail.com");
        mailMessage.setText("Toplantı talebiniz reddedilmiştir.\nTalep Edilen Toplantının Bilgileri: " +
                "\nTarih: " + request.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\nSaat: " + request.getStartTime() + " - " + request.getEndTime() +
                "\nBina: " + request.getMeetingRoom().getBuilding().getBuildingName() + "\nOda: " + request.getMeetingRoom().getMeetingRoomName() +
                "\nToplantı Türü: " + request.getMeetingType().getMeetingTypeName() +
                "\nToplantı Açıklaması: " + request.getDescription());

        // Send the email
        sendEmail(mailMessage);
    }


    @Override
    @Async
    public void sendNotificationEmail(MeetingRequest request) {
        Role role = roleService.getRole("SUPERVISOR");
        List<Member> members = memberService.getAllByRole(role);
        InternetAddress[] Address = new InternetAddress[members.size()];
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Yeni Bir Toplantı Talebi Yapıldı");
        mailMessage.setFrom("dijital.toplanti@gmail.com");
        mailMessage.setText(request.getCreator().getFirstName() + " " + request.getCreator().getLastName() + " tarafından yeni bir toplantı talebi yapıldı.\nTalep Edilen Toplantının Bilgileri: " +
                "\nTarih: " + request.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\nSaat: " + request.getStartTime() + " - " + request.getEndTime() +
                "\nBina: " + request.getMeetingRoom().getBuilding().getBuildingName() + "\nOda: " + request.getMeetingRoom().getMeetingRoomName() +
                "\nToplantı Türü: " + request.getMeetingType().getMeetingTypeName() +
                "\nToplantı Açıklaması: " + request.getDescription());

        // Send the email
        /*sendEmail(mailMessage);*/

        for (int i = 0; i < members.size(); i++) {
            try {
                Address[i] = new InternetAddress(members.get(i).getEmail());
            } catch (AddressException e) {
                e.printStackTrace();
            }
            if (!request.getCreator().getEmail().equals(Address[i].toString())) {
                mailMessage.setTo(Address[i].toString());
                sendEmail(mailMessage);
            }
        }

    }

}