package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * UNKNOWN
 *
 * @author Owen Peterson
 */
@Autonomous(name="Testing Autonomous")
public class TestAuto extends SPQRLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        this.hardwareInit();

        waitForStart();

        if(!isStopRequested() && opModeIsActive()) {

            this.drive(30, this.speed);
            this.drive(300, this.speed);
            this.drive(3000, this.speed);

            this.turn2(90, 0.25);
            this.turn2(-90, 0.25);
            this.turn2(90, this.speed);
            this.turn2(-90, this.speed);



            this.turn(90, 0.25);
            this.turn(-90, 0.25);
            this.turn(90, this.speed);
            this.turn(-90, this.speed);
            updateTelemetry();
        } updateTelemetry();
    }
}