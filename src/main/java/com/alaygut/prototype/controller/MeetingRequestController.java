package com.alaygut.prototype.controller;

import javax.validation.Valid; 

import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.dto.MeetingDetail;
import com.alaygut.prototype.service.RoomFeatureService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.service.MeetingRequestService;
import com.alaygut.prototype.service.MeetingRoomService;
import com.alaygut.prototype.service.MemberService;
import java.security.Principal;
import java.util.List;
import java.util.Map;


@Controller
public class MeetingRequestController {
	private MeetingRequestService meetingRequestService;
	private MeetingRoomService meetingRoomService;
	private MemberService memberService;
	private RoomFeatureService roomFeatureService;

	public MeetingRequestController(MeetingRequestService meetingRequestService, MeetingRoomService meetingRoomService, MemberService memberService, RoomFeatureService roomFeatureService) {
		this.meetingRequestService = meetingRequestService;
		this.meetingRoomService = meetingRoomService;
		this.memberService = memberService;
		this.roomFeatureService = roomFeatureService;
	}

	@GetMapping("/add/meetingRequest")
	public ModelAndView addMeetingRequestPage() {
		return new ModelAndView(
				"addMeetingRequest",
				"addMeetingRequestForm",
				meetingRequestService.getAddMeetingRequestForm());
	}
	
	@PostMapping("/add/meetingRequest")
	public String handleAddMeetingRequest(@Valid @ModelAttribute("addMeetingRequestForm") AddMeetingRequestForm addMeetingRequestForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			meetingRequestService.setExternalData(addMeetingRequestForm);
			return "/addMeetingRequest";
		}
		if (meetingRequestService.addRequest(addMeetingRequestForm))
			redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı talebi başarıyla oluşturuldu.");
		return "redirect:/add/meetingRequest";
	}
	
	@GetMapping("/list/meetingRequest")
	public ModelAndView listMeetingRequestsPage() {
		return new ModelAndView("listMeetingRequests", "meetingRequestDetailProvider", meetingRequestService.getListMeetingRequestsDetailProvider());
	}

	@GetMapping("/list/pendingRequest")
	public ModelAndView listPendingMeetingRequestsPage() {
		return new ModelAndView("pendingRequests", "meetingRequestDetailProvider", meetingRequestService.getPendingMeetingRequestsDetailProvider());
	}

	@GetMapping("/getBuildingMeetingRooms")
	public @ResponseBody Map<String, String> getBuildingMeetingRooms(@RequestParam("buildingId") Long buildingId){
		return meetingRequestService.getBuildingMeetingRooms(buildingId);
	}

	@GetMapping("/getMeetingRoomCapacity")
	public @ResponseBody int getMeetingRoomCapacity(@RequestParam("meetingRoomId") Long meetingRoomId){
		return meetingRoomService.getMeetingRoom(meetingRoomId).getCapacity();
	}

	@PostMapping("/accept/meetingRequest/{meetingRequestId}")
	public String acceptMeetingRequest(@PathVariable Long meetingRequestId, Principal principal){
		Member supervisor = memberService.getMember(principal.getName());
		meetingRequestService.acceptMeetingRequest(meetingRequestId, supervisor.getMemberId());
		return "redirect:/list/pendingRequest";
	}

	@PostMapping("/decline/meetingRequest/{meetingRequestId}")
	public String declineMeetingRequest(@PathVariable Long meetingRequestId, Principal principal){
		Member supervisor = memberService.getMember(principal.getName());
		meetingRequestService.declineMeetingRequest(meetingRequestId, supervisor.getMemberId());
		return "redirect:/list/pendingRequest";
	}

	@GetMapping("/member/meetingRequest")
	public ModelAndView getMemberMeetingRequestsPage(Principal principal){
		Member member = memberService.getMember(principal.getName());
		return new ModelAndView("memberMeetingRequests", "meetingRequestDetailProvider", meetingRequestService.getMemberMeetingRequestDetailsProvider(member));
	}

	@GetMapping("/getGridData")
	public @ResponseBody List<MeetingDetail> getMeetingRoomCapacity(@RequestParam("date") String date, @RequestParam("meetingRoomId") String meetingRoomId){
		return meetingRequestService.getGridData(date, meetingRoomId);
	}

	@GetMapping("/loadAllFeatures")
	public @ResponseBody Map<Long, String> loadAllFeatures(){
		return roomFeatureService.getFeatureMap();
	}

	@GetMapping("/filterMeetingRooms")
	public @ResponseBody Map<Long, String> filterMeetingRooms(@RequestParam("capacity") String capacity){
		return meetingRoomService.filterMeetingRoomsByCapacityAndFeatures(capacity);
	}

	@PostMapping("/cancelMeetingRequest/{meetingRequestId}")
	public String cancel(@PathVariable Long meetingRequestId) {
		meetingRequestService.cancel(meetingRequestId);
		return "redirect:/member/meetingRequest";
	}

	@GetMapping("/loadMeetingRoomProperties")
	public @ResponseBody List<String> loadMeetingRoomProperties(@RequestParam("meetingRoomId") Long meetingRoomId){
		return meetingRoomService.loadMeetingRoomProperties(meetingRoomId);
	}

	@PostMapping("/requestMeetingFromUser")
	public String requestMeetingFromUser(@Valid @ModelAttribute("addMeetingRequestForm") AddMeetingRequestForm addMeetingRequestForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		if(bindingResult.hasErrors()) {
			meetingRequestService.setExternalData(addMeetingRequestForm);
			return "/addMeetingRequest";
		}
		if (meetingRequestService.requestFromUser(addMeetingRequestForm))
			redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı talebi kullanıcıya gönderildi.");
		else
			redirectAttributes.addFlashAttribute("infoMessage", "Bu toplantı için talebiniz zaten mevcut.");
		return "redirect:/add/meetingRequest";
	}

	@GetMapping("/member/requests")
	public ModelAndView getMemberToMemberRequests(Principal principal){
		return new ModelAndView("memberRequests", "meetingRequestDetailProvider", meetingRequestService.getMemberToMemberMeetingRequestDetailsProvider(memberService.getMember(principal.getName())));
	}

	@PostMapping("/acceptMemberRequest/{meetingRequestId}")
	public String acceptMemberRequest(@PathVariable Long meetingRequestId){
		meetingRequestService.acceptMemberRequest(meetingRequestId);
		return "redirect:/member/requests";
	}

	@PostMapping("/declineMemberRequest/{meetingRequestId}")
	public String declineMemberRequest(@PathVariable Long meetingRequestId, Principal principal){
		meetingRequestService.declineMemberRequest(meetingRequestId, memberService.getMember(principal.getName()));
		return "redirect:/member/requests";
	}

	@GetMapping("/getOtherMemberRequests")
	public @ResponseBody int otherMemberRequests(Principal principal){
		return meetingRequestService.otherMemberRequestNumber(memberService.getMember(principal.getName()));
	}
}
