package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Simple Auto")
public class RedSimpleAuto extends SPQRLinearOpMode {

    private int[] tapeColor = {2970, 1890, 700};
    private Dir directionOne = Dir.RIGHT;
    private Dir directionTwo = Dir.LEFT;

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {
            this.driveForTime(this.speed, justSpeed(250, this.speed));

            this.turnForTime90(directionOne, this.turnSpeed);

            this.driveForTime(this.speed, justSpeed(3050, this.speed));

            this.turnForTime90(directionTwo, this.turnSpeed);

            this.driveForTime(this.speed, this.justSpeed(570, this.speed));

            this.robot.tow.setPosition(1);

            this.sleep(1000);

            this.driveForTime(-this.speed, this.justSpeed(1200, this.speed));

            this.robot.tow.setPosition(0);

            this.driveForTime(-this.speed, this.justSpeed(60, this.speed));

            this.turnForTime90(directionTwo, this.turnSpeed);

            this.driveForTime(this.speed, this.justSpeed(1000, this.speed));

            this.turnForTime90(directionOne, this.turnSpeed);

            this.driveForTime(speed, this.justSpeed(1200, this.speed));

            this.turnForTime90(directionOne, this.turnSpeed);

            this.driveForTime(this.speed, this.justSpeed(1400, this.speed));

            this.turnForTime90(directionOne, this.turnSpeed);

            this.driveForTime(this.speed, this.justSpeed(500, this.speed));

            this.driveForTime(-this.speed, this.justSpeed(300, this.speed));

            this.turnForTime90(directionOne, this.turnSpeed);

            this.stopAtTape(this.tapeColor, 500);
        }
    }
}