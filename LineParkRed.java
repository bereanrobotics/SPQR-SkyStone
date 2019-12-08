package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Line Park Red")
public class LineParkRed extends SPQRLinearOpMode {

    private int[] tapeColor = {2600, 1660, 600};

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {
            this.sleep(10000);
            this.stopAtTape(this.tapeColor, 500);
        }
    }
}