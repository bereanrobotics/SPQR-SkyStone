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
 *  - Grab another block.
 *  - Deliver the block.
 *  - Park in the outside lane on the line under the red bridge.
 *
 * @author Owen Peterson
 */
@Autonomous(name="'Trumpet' Red -bot (1) Block (2) Block (3) Line -out", group="Red")
public class Trumpet extends SPQRLinearOpMode {

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
            this.drive(12000, 1.0);

            //turn in preparation to release the block
            this.turn(90, 1.0);

            //releasing the block, with a slight pause to make sure it is clear of the tow
            //set tow down
            this.sleep(100);

            this.drive(-1500, -1.0);

            this.turn(90, 1.0);

            this.drive(10000, 1.0);

            this.turn(-90, 1.0);
            //strafe to the right, all the way to the top of the field
            this.drive(2700, 1.0);

            this.drive(800, 0.5);

            //set tow down
            this.sleep(1000);

            //reverse away from the blocks
            this.drive(-2500, -1);

            //turn right towards the top of the field
            this.turn(-90, 1.0);

            //drive across the line
            this.drive(8000, 1.0);

            //releasing the block, with a slight pause to make sure it is clear of the tow
            //set tow down

        }
    }
}
