package org.firstinspires.ftc.teamcode;

public class Claw {
    double Level ;
    String Id;
    double maxLength;
    double minLength;

    public Claw(String Id){
        this.Id = Id;
    }
    public void setMaxLength(double maxLength){
        this.maxLength = maxLength;
    }
    public static void setMinLength(double minLength){
        //this.minLength = minLength;
    }

    /*public static double getLevel (){
        return;
    }

    public void increaseTier (){
        double currentLevel = Claw.getLevel();
    }*/
}
