package com.alaygut.prototype.dto;

import javax.validation.constraints.Size;

public class AddRightForm {

    @Size(min = 3, max = 20, message = "Yetki adı [3-20] karakter uzunluğunda olmalıdır.")
    private String rightName;

    private String description;

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
