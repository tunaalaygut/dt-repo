package com.alaygut.prototype.domain;

import com.fasterxml.jackson.databind.ser.Serializers;

import javax.persistence.*;
import java.util.Set;

@Entity
public class RoomFeature extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "featureId")
    private Long featureId;

    @Column (name="name")
    private String featureName;

    @Column (name="description")
    private String description;


    @ManyToMany(targetEntity = MeetingRoom.class)
    @JoinColumn(name = "meetingRoomId")
    private Set meetingRoomSet;

    public RoomFeature() {

    }

    public RoomFeature(String featureName, String description) {
        this.featureName=featureName;
        this.description=description;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
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
