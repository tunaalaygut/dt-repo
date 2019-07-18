package com.alaygut.prototype.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Building extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buildingId")
    private Long buildingId;

    //@UniqueBuildingName(message = "Bina ismi mevcut.")
    @Column(name = "buildingName",unique = true)
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
