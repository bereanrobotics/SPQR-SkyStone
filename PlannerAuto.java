package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Planner Auto 1")
public class PlannerAuto extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //AUTO GENERATED CODE
            //TOKEN: UGxhbm5lckF1dG88PlBsYW5uZXIgQXV0byAxIzwkPiM1NTkvfj9+LzQ0NS9+P34vMSM8JD4jNDcwL34/fi80NDUvfj9+LzEjPCQ+IzQ3MC9+P34vMzAzL34/fi8x
            this.drive(5425.44, 1);
            this.turn(270, 1.0);
            this.drive(8656.32, 1);
            this.turn(270, 1.0);
            this.drive(8656.32, 1);
            this.turn(270, 1.0);
        }
    }
}