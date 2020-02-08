package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Autonomous: RED ALLIANCE
 *
 * Start against the wall in such a way that the right edge of the robot is next to the red building
 * zone.
 *
 * Steps:
 *  - Move the red foundation into the building zone.
 *  - Park in the inside lane on the line under the red bridge.
 *
 * @author Owen Peterson
 */
@Autonomous(name="1 'Sunflower' Red -top (1) Foundation (2) Line -in", group="Red")
public class Sunflower extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //drive forward, to near the foundation
            this.drive(5500, 1);

            //strafe to the top of the field
            this.strafe(Dir.RIGHT, 3000, 1);

            //drive forward to the foundation
            this.drive(2600, 0.5);

            //set tow down
            this.robot.dropTow();
            this.sleep(1000);

            //pull back the foundation to the building site
            this.drive(-6900, -0.4);

            this.robot.raiseTow();

            //strafe to the line
            this.strafe(Dir.LEFT, 41000, 1.0);
            this.drive(1500, 1);
            this.strafe(Dir.LEFT, 4100, 1.0);

            this.drive(10300, 1.0);

            this.strafe(Dir.RIGHT, 8000, 1.0);

            //drive backwards towards the outside
            this.drive(-5500, -1.0);

            this.strafe(Dir.LEFT, 10400, 1.0);
        }

//        if (opModeIsActive() && !isStopRequested()) {
//
//            //drive forward, to near the foundation
//            this.drive(5500, 1);
//
//            //strafe to the top of the field
//            this.strafe(Dir.RIGHT, 2500, 1);
//
////          this.turn(-5, 1);
//
//            //drive forward to the foundation
//            this.drive(2500, 0.5);
//
//            //drop the tow on the foundation, wait until complete
//            //tow down
//            this.sleep(1000);
//
//            //pull back the foundation to the building site
//            this.drive(-8200, -0.4);
//
//            //retracting the tow
//            //tow up
//
//            //strafe to the line
//            this.strafe(Dir.LEFT, 10000, 1.0);
//
//            this.drive(7000, 1.0);
//
//            this.strafe(Dir.RIGHT, 8000, 1.0);
//            //drive backwards towards the outside
//
//            this.drive(-3000, -1.0);
//
//            this.strafe(Dir.LEFT, 12000, 1.0);
//            this.drive(-2000, -0.5);
//        }
    }
}
