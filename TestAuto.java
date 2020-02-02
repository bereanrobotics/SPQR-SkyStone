package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Test Auto")
public class TestAuto extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //AUTO GENERATED CODE
            //TOKEN: VGVzdEF1dG88PlRlc3QgQXV0byM8JD4jNTYyL34/fi80Mzkvfj9+LzEjPCQ+IzUwNC9+P34vNDM5L34/fi8tMSM8JD4jNTA0L34/fi8xOTcvfj9+LzE=
            this.drive(3535.68, 1);
            this.turn(450, 1.0);
            this.drive(14752.32, -1);
        }
    }
}