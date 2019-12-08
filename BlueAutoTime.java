package org.firstinspires.ftc.teamcode;

public class BlueAutoTime extends SPQRLinearOpMode {


    @Override
    public void runOpMode(){
        this.hardwareInit();

        waitForStart();

        this.driveForTime(this.speed, 550);
        this.robot.tow.setPosition(1);
    }
}

