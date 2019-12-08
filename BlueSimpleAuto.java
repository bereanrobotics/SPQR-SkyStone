package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import static java.lang.Math.pow;

@Autonomous(name="Blue Simple Auto")
@Disabled
public class BlueSimpleAuto extends SPQRLinearOpMode {

    private int[] tapeColor = {846, 2137, 2515};
    private Dir directionOne = Dir.LEFT;
    private Dir directionTwo = Dir.RIGHT;


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