package com.alaygut.prototype.dto;

import com.alaygut.prototype.domain.MeetingRoom;

import javax.validation.constraints.Size;
import java.util.Set;

public class AddRoomFeatureForm extends FormBase {
    @Size(min=5, max= 50, message = "Özellik ismi 5-50 karakter uzunluğunda olmalıdır.")
    private String featureName;

    private  String description;

    //sanırım olmayacak
    private Set<MeetingRoom> meetingRooms;

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Set<MeetingRoom> getMeetingRooms() { return meetingRooms; }

    public void setMeetingRooms(Set<MeetingRoom> meetingRooms) { this.meetingRooms = meetingRooms; }
}
