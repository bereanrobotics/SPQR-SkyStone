package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Autonomous: BLUE ALLIANCE
 *
 * Start against the wall in such a way that the left edge of the robot is next to the blue building
 * zone.
 *
 * Steps:
 *  - Move the blue foundation into the building zone.
 *  - Park in the outside lane on the line under the blue bridge.
 *
 * @author Owen Peterson
 */
@Autonomous(name="'Jane' Blue -top (1) Foundation (2) Line -out", group="Blue")
public class Jane extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //AUTO GENERATED CODE
            //TOKEN: QXV0b1JlZFRvcEZvdW5kYXRpb25MaW5lPD5SZWQgLXRvcCAoMSkgRm91bmRhdGlvbiAoMikgTGluZSM8JD4jNTYwL34/fi8xMzcvfj9+LzEjPCQ+IzQ2Ny9+P34vMTM4L34/fi8xIzwkPiM0NjUvfj9+LzQ0L34/fi8xIzwkPiM0MTQvfj9+LzQ0L34/fi8xIzwkPiM1NTAvfj9+LzQ0L34/fi8xIzwkPiM1NTIvfj9+LzMwNS9+P34vMQ==

            //drive forward, to near the foundation
            this.drive(5500, 1);

            //strafe to the top of the field
            this.strafe(Dir.LEFT, 1000, 1);

            //drive forward to the foundation
            this.drive(2600, 0.5);

            //drop the tow on the foundation, wait until complete
            //set tow down
            this.sleep(1000);

            //pull back the foundation to the building site
            this.drive(-8200, -0.4);

            //retracting the tow
            //set tow down

            //strafe to the line
            this.strafe(Dir.RIGHT, 10000, 1.0);

            this.drive(7000, 1.0);

            this.strafe(Dir.LEFT, 8000, 1.0);
            //drive backwards towards the outside

            this.drive(-3000, -1.0);

            this.strafe(Dir.RIGHT, 12000, 1.0);
            this.drive(-2000, -0.5);
        }
    }
}
