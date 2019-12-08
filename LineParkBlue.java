package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Line Park Blue")
public class LineParkBlue extends SPQRLinearOpMode {

    private int[] tapeColor = {880, 2000, 2300};

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