package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.System.*;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.pow;

@Autonomous(name="Blue Simple Auto")
public class BlueSimpleAuto extends SPQRLinearOpMode {
    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if(opModeIsActive() && !isStopRequested()) {
            this.driveForTime(this.speed, justSpeed(250, this.speed));
            this.turnForTime(Dir.RIGHT, this.turnSpeed, justSpeed(this.ninetyDegreeTime, this.turnSpeed));
            this.driveForTime(this.speed, justSpeed(3250, this.speed));
            this.turnForTime(Dir.LEFT, this.turnSpeed, justSpeed(this.ninetyDegreeTime, this.turnSpeed));
            this.driveForTime(this.speed, this.justSpeed(550, this.speed));
            this.robot.tow.setPosition(1);
            this.sleep(1000);
            this.driveForTime(-this.speed, this.justSpeed(1000, this.speed));
            this.robot.tow.setPosition(0);
            this.turnForTime(Dir.LEFT, this.turnSpeed, justSpeed(this.ninetyDegreeTime, this.turnSpeed));
            this.driveForTime(this.speed, this.justSpeed(1400, this.speed));
            this.turnForTime(Dir.RIGHT, this.turnSpeed, justSpeed(this.ninetyDegreeTime, this.turnSpeed));
            this.driveForTime(speed, this.justSpeed(1000, this.speed));
            this.turnForTime(Dir.RIGHT, this.turnSpeed, justSpeed(this.ninetyDegreeTime, this.turnSpeed));
            this.driveForTime(this.speed, this.justSpeed(1400, this.speed));
            this.turnForTime(Dir.RIGHT, this.turnSpeed, justSpeed(this.ninetyDegreeTime, this.turnSpeed));
            this.driveForTime(this.speed, this.justSpeed(400, this.speed));
            this.driveForTime(-this.speed, this.justSpeed(250, this.speed));
            this.turnForTime(Dir.RIGHT, this.turnSpeed, justSpeed(this.ninetyDegreeTime, this.turnSpeed));
            this.driveForTime(this.speed, justSpeed(900, this.speed));
            this.turnForTime(Dir.LEFT,this.turnSpeed, justSpeed(this.ninetyDegreeTime,this.turnSpeed));
            this.driveForTime(this.speed, justSpeed(400, this.speed));
            this.turnForTime(Dir.RIGHT, this.turnSpeed, justSpeed(this.ninetyDegreeTime,this.turnSpeed));
            this.driveForTime(speed,justSpeed(300, speed));
            if (true){
                this.robot.setPowers(0);
            }
        }
    }