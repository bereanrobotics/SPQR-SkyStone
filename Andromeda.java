
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Autonomous: BLUE ALLIANCE
 *
 * Start against the wall in such a way that the right edge of the robot is next to the red depot.
 *
 * Steps:
 *  - Grab a block.
 *  - Deliver the block.
 *  - Grab another block.
 *  - Deliver the block.
 *  - Park in the outside lane on the line under the blue bridge.
 *
 * @author Owen Peterson
 */
@Autonomous(name="'Andromeda' Blue -bot (1) Block (2) Block (3) Line -out", group="Blue")
public class Andromeda extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //Initial movement forward
            this.drive(7000, 1);

            //grab the block using the tow, wait for the tow to come down before
            this.robot.dropTow();
            this.sleep(250);

            //reverse away from the blocks
            this.drive(-2500, -1);

            //turn right towards the top of the field
            this.turn(90, 1.0);

            //drive across the line
            this.drive(12000, 1.0);

            //turn in preparation to release the block
            this.turn(-90, 1.0);

            //releasing the block, with a slight pause to make sure it is clear of the tow
            this.robot.raiseTow();
            this.sleep(100);

            this.drive(-1500, -1.0);

            this.turn(-90, 1.0);

            this.drive(10000, 1.0);

            this.turn(90, 1.0);
            //strafe to the right, all the way to the top of the field
            this.drive(2700, 1.0);

            this.drive(800, 0.5);

            //set tow down
            this.robot.dropTow();
            this.sleep(1000);

            //reverse away from the blocks
            this.drive(-2500, -1);

            //turn right towards the top of the field
            this.turn(90, 1.0);

            //drive across the line
            this.drive(8000, 1.0);

            //releasing the block, with a slight pause to make sure it is clear of the tow
            this.robot.raiseTow();

        }
    }
}
