package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="Blue Auto Time")
public class BlueAutoTime extends SPQRLinearOpMode {


    @Override
    public void runOpMode(){
        this.hardwareInit();

        waitForStart();

        this.driveForTime(this.speed, 100);
        this.turnForTime(Dir.LEFT, this.speed, 800);
        this.driveForTime(this.speed, 800);
        this.turnForTime(Dir.RIGHT, this.speed, 800);
        this.driveForTime(this.speed, 500);
        this.robot.tow.setPosition(1);
        this.sleep(1000);
        this.driveForTime(-this.speed, 1750);
    }
}

