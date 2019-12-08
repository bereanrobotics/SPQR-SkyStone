package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Red Simple Auto")
public class RedSimpleAuto extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if(opModeIsActive() && !isStopRequested()) {
            this.driveForTime(this.speed, 500);
            this.turnForTime(Dir.RIGHT, this.turnSpeed, this.ninetyDegreeTime);
            this.driveForTime(this.speed, 7000);
            this.turnForTime(Dir.LEFT,this.turnSpeed, this.ninetyDegreeTime);
            this.driveForTime(speed, 2000);
            this.sleep(200);
            //this.robot.tow.setPosition(1);
            this.driveForTime(-this.speed, 1500);
            //this.robot.tow.setPosition(0);
            this.turnForTime(Dir.LEFT, this.turnSpeed, this.ninetyDegreeTime);
            this.driveForTime(speed, 3000);
            this.turnForTime(Dir.RIGHT, this.turnSpeed, this.ninetyDegreeTime);
            this.driveForTime(speed, 1000);
            this.turnForTime(Dir.RIGHT, this.turnSpeed, this.ninetyDegreeTime);
            this.driveForTime(speed, 3000);
            this.turnForTime(Dir.RIGHT, this.turnSpeed, this.ninetyDegreeTime);
            this.driveForTime(this.speed, 1000);
            this.driveForTime(-this.speed, 500);
            this.turnForTime(Dir.RIGHT, this.turnSpeed, this.ninetyDegreeTime);
            this.driveForTime(speed, 2500);
            if (true){
                this.robot.setPowers(0);
            }
        }
    }
}

