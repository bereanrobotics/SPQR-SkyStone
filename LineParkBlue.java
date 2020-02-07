package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Autonomous: BLUE ALLIANCE
 *
 * Start against the wall in such a way that the robot will cross under the audience-left bridge.
 *
 * Steps:
 *  - Wait 10 seconds.
 *  - Drive forward.
 *  - Stop at blue line under the audience-left bridge.
 *
 * @author Arkin Solomon
 */
@Autonomous(name="Line Park Blue",group="Blue")
public class LineParkBlue extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {
            this.sleep(10000);
            this.stopAtTape(TapeColors.blue, 500);
        }
    }
}