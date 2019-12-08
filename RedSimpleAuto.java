package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Simple Auto")
public class RedSimpleAuto extends SPQRLinearOpMode {

    private int[] tapeColor = {2970, 1890, 700};

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {
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

            this.turnForTime(Dir.LEFT, this.turnSpeed, justSpeed(this.ninetyDegreeTime, this.turnSpeed));

            this.driveForTime(this.speed, justSpeed(400, this.speed));

            this.turnForTime(Dir.RIGHT, this.turnSpeed, justSpeed(this.ninetyDegreeTime, this.turnSpeed));

            this.driveForTime(speed, justSpeed(300, speed));

            this.stopAtTape(this.tapeColor, 500);
        }
    }
}