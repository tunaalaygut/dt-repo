package com.alaygut.prototype.dto;

import com.alaygut.prototype.annotation.UniqueBuildingName;  

import javax.validation.constraints.Size;


public class AddBuildingForm extends FormBase {
    @Size(min = 3, max = 50, message = "{buildingName.size.not.valid}")
    @UniqueBuildingName(message = "{buildingName.not.unique}")
    private String buildingName;

    @Size(min=3, message = "{buildingAddr.size.not.valid}")
    private String buildingAddr;

    @Size(min = 3, max = 50, message = "{newBuildingName.size.not.valid}")
    private String newBuildingName;

    private String originalName;

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

    public String getNewBuildingName() {
        return newBuildingName;
    }

    public void setNewBuildingName(String newBuildingName) {
        this.newBuildingName = newBuildingName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
}
