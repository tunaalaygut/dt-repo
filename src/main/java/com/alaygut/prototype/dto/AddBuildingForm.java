package com.alaygut.prototype.dto;

import javax.validation.constraints.Size;


public class AddBuildingForm extends FormBase {
    @Size(min = 3, max = 50, message = "Bina ismi 3-50 karakter uzunluğunda olmalıdır.")
    private String buildingName;

    private String buildingAddr;

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
