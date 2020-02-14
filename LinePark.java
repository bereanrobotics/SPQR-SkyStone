package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="LinePark -top (1) Line -out", group="Both")
public class LinePark extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //AUTO GENERATED CODE
            //TOKEN: TGluZVBhcms8PkxpbmVQYXJrIzwkPiM1NjIvfj9+LzEzMy9+P34vMSM8JD4jNTY2L34/fi8zMDEvfj9+LzE
            this.sleep(10000);
            this.drive(3000, 1);
            this.robot.dropTow();
        }
    }
}