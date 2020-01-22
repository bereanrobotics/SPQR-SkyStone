package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Testing Autonomous")
public class TestAuto extends SPQRLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        this.hardwareInit();

        waitForStart();

        if(!isStopRequested() && opModeIsActive()) {
            this.drive(3000, this.speed);
            updateTelemetry();
        } updateTelemetry();
    }
}