package com.alaygut.prototype.domain;

import javax.persistence.*;

@Entity
public class Building extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buildingId")
    private Long buildingId;

    @Column(name = "buildingName")
    private String buildingName;

    @Column(name = "buildingAddr")
    private String buildingAddr;

    public Building(String buildingName, String buildingAddr) {
        this.buildingName = buildingName;
        this.buildingAddr = buildingAddr;
    }

    public Building() {
        //default constructor
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingAddr() {
        return buildingAddr;
    }

    public void setBuildingAddr(String buildingAddr) {
        this.buildingAddr = buildingAddr;
    }
}
