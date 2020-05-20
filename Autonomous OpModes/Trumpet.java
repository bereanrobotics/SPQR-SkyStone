package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Autonomous: RED ALLIANCE
 *
 * Start against the wall in such a way that the left edge of the robot is next to the blue depot.
 *
 * Steps:
 *  - Grab a block.
 *  - Deliver the block.
 *  - Park in the outside lane on the line under the red bridge.
 *
 * @author Owen Peterson
 */
@Autonomous(name="3 'Trumpet' Red -bot (1) Block (2) Line -out", group="Red")
public class Trumpet extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //Initial movement forward
            this.drive(7000, 1);

            this.drive(1000, 0.5);
            //grab the block using the tow, wait for the tow to come down before
            this.robot.dropTow();
            this.sleep(1000);

            //reverse away from the blocks
            this.drive(-3750, -1);

            //turn right towards the top of the field
            this.turn(-90, 1.0);

            //drive across the line
            this.drive(15000, 1.0);

            //releasing the block, with a slight pause to make sure it is clear of the tow
            this.robot.raiseTow();
            this.sleep(1000);

            this.drive(-5000, -1);

        }
    }
}
