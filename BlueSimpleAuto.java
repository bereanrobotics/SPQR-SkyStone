package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.System.*;
import java.util.Locale;

@Autonomous(name="Blue Simple Auto")
public class BlueSimpleAuto extends LinearOpMode {
    private boolean isInitialized = false;
    private HardwareSPQR robot = new HardwareSPQR();
    private double nsPerS = 2000000000;

    private double timeStarted;

    private double speed = -0.5;


    public double getElaspedTime(double start){ //returns time in seconds for how long the opmode has been running
        double elaspedTime = (System.nanoTime()/nsPerS) - start;
        return elaspedTime;
    }

    public void goForward (double time) { //go forward for
        updateTelemtry ();
        for(double startTime = getElaspedTime(timeStarted); (getElaspedTime(startTime) < time) && opModeIsActive();) {
            this.robot.setPowers(speed);
            updateTelemtry ();
        }
        this.robot.setPowers(0);
        updateTelemtry ();
    }

    public void updateTelemtry () {
        telemetry.addData("Seconds Passed","%.5g%n", getElaspedTime(timeStarted));
        telemetry.update();
    }
    @Override
    public void runOpMode() {
        if (!isInitialized) {
            isInitialized = true;
            this.robot.init(hardwareMap);
//            this.robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        waitForStart();
        if(opModeIsActive() && !isStopRequested()) {
            timeStarted = System.nanoTime() / nsPerS;
            updateTelemtry ();
            goForward(1);
            requestOpModeStop();
        }
    }
}
