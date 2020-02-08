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
 *  - Move the foundation into the building site.
 *  - Strafe out of the small gap.
 *  - Park in the outside lane on the line under the blue bridge.
 *
 * @author Owen Peterson
 */
@Autonomous(name="'Bear' Red -bot (1) Block (2) Foundation (3) Line -out", group="Red")
public class Bear extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //AUTO GENERATED CODE
            //TOKEN: UmVkQm90QmxvY2tGb3VuZGF0aW9uTGluZTw+UmVkIC1ib3QgKDEpIEJsb2NrICgyKSBGb3VuZGF0aW9uICgzKSBMaW5lIzwkPiM1NjAvfj9+LzQ2Ni9+P34vMSM8JD4jNDU2L34/fi80NjYvfj9+LzEjPCQ+IzQ1NC9+P34vNTEvfj9+LzEjPCQ+IzU2Mi9+P34vNTAvfj9+LzEjPCQ+IzU2Mi9+P34vMjk3L34/fi8x

            //Initial movement forward
            this.drive(7000, 1);
            //slow the robot down and move right up to the block
            this.drive(900,0.5);

            //grab the block using the tow, wait for the tow to come down before
            //set tow down
            this.sleep(1000);

            //reverse away from the blocks
            this.drive(-2500, -1);

            //turn right towards the top of the field
            this.turn(-90, 1.0);

            //drive across the line
            this.drive(16000, 1.0);

            //turn in preparation to release the block
            this.turn(90, 1.0);

            //releasing the block, with a slight pause to make sure it is clear of the tow
            //set tow down
            this.sleep(100);

            //strafe to the right, all the way to the top of the field
            this.strafe(Dir.RIGHT, 11000, 1);

            //drive forward up to the foundation
            this.drive(2700, 1);

            //grab the foundation and wait for it to settle
            //set tow down
            this.sleep(900);

            //drive back slowly pulling the foundation into the building site
            this.drive(-9250, -0.5);

            //retract the tow
            //set tow down

            //strafe to the left all the way to the line
            this.strafe(Dir.LEFT, 16750, 1.0);

            //drive in until time runs out (usually does during this function)
            this.drive(3000, 1.0);
        }
    }
}
