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
@Autonomous(name="1 'Jane' Blue -top (1) Foundation (2) Line -out", group="Blue")
public class Jane extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //drive forward, to near the foundation
            this.drive(5500, 1);

            //strafe to the top of the field
            this.strafe(Dir.LEFT, 2500, 1);

            //drive forward to the foundation
            this.drive(2600, 0.5);

            //set tow down
            this.robot.dropTow();
            this.sleep(1000);

            //pull back the foundation to the building site
            this.drive(-7000, -0.4);

            this.robot.raiseTow();

            //strafe to the line
            this.strafe(Dir.RIGHT, 4000, 1.0);
            this.drive(1000, 1);
            this.strafe(Dir.RIGHT, 4000, 1.0);

            this.drive(10300, 1.0);

            this.strafe(Dir.LEFT, 8000, 1.0);

            //drive backwards towards the outside
            this.drive(-5500, -1.0);

            this.strafe(Dir.RIGHT, 10400, 1.0);
        }
    }
}
