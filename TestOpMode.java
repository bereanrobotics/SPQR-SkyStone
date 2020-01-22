package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Test OpMode")
public class TestOpMode extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //AUTO GENERATED CODE
            drive(335.28000000000003, 1.0);
            turn(270, 1.0);
            drive(865.6320000000001, 1.0);
        }
    }
}