package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Autonomous: RED ALLIANCE
 *
 * Start against the wall in such a way that the robot will cross under the audience-right bridge.
 *
 * Steps:
 *  - Wait 10 seconds.
 *  - Drive forward.
 *  - Stop at blue line under the audience-right bridge.
 *
 * @author Arkin Solomon
 */
@Autonomous(name="Line Park Red", group="Red")
@Disabled
public class LineParkRed extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {
            this.sleep(10000);
            this.stopAtTape(TapeColors.red, 500);
        }
    }
}