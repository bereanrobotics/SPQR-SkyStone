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
            this.drive(5000,1);
            this.turn(-90,1);
            this.sleep(15000);
            this.turn(-90,1);
            this.drive(5000,1);
            this.turn(-90,1);
            this.drive(5000,1);
            this.turn(-90,1);
            updateTelemetry();
        } updateTelemetry();
    }
}