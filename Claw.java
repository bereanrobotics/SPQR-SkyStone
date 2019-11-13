package org.firstinspires.ftc.teamcode;

public class Claw {
    double Level;
    String Id;
    double maxLength;
    double minLength;

    public Claw(String Id){
        this.Id = Id;
    }
    public void setMaxLength(double maxLength){
        this.maxLength = maxLength;
    }
    public void setMinLength(double minLength){
        this.minLength = minLength;
    }
}
