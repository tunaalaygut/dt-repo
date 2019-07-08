package com.alaygut.prototype.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class RoomFeature extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomFeatureId")
    private Long roomFeatureId;

    @Column (name="name")
    private String featureName;

    @Column (name="description")
    private String description;


    @ManyToMany(targetEntity = MeetingRoom.class)
    @JoinTable(name = "Meeting_Room_Feature",
            joinColumns = @JoinColumn(name = "roomFeatureId"),
            inverseJoinColumns = @JoinColumn(name = "meetingRoomId"))
    private Set meetingRoomSet;

    public RoomFeature() {

    }

    public RoomFeature(String featureName, String description) {
        this.featureName=featureName;
        this.description=description;
    }

    public Long getRoomFeatureId() {
        return roomFeatureId;
    }

    public void setRoomFeatureId(Long roomFeatureId) {
        this.roomFeatureId = roomFeatureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set getMeetingRoomSet() {
        return meetingRoomSet;
    }

    public void setMeetingRoomSet(Set meetingRoomSet) {
        this.meetingRoomSet = meetingRoomSet;
    }
}
