package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.System.*;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.pow;

@Autonomous(name="Blue Simple Auto")
public class BlueSimpleAuto extends LinearOpMode {
    private boolean isInitialized = false;
    private HardwareSPQR robot = new HardwareSPQR();
    private double nsPerS = 1*pow(10, 9);

    private double timeStarted;

    private double speed = -0.5;


    public double getElaspedTime(double start){ //returns time in seconds for how long the opmode has been running
        double elaspedTime = (System.nanoTime()/nsPerS) - start;
        return elaspedTime;
    }

    public void forwardTime(double time, double speed) { //go forward for
        updateTelemtry ();
        for(double startTime = getElaspedTime(timeStarted); (getElaspedTime(startTime) < time) && opModeIsActive();) {
            this.robot.setPowers(speed);
            updateTelemtry ();
        }
        this.robot.setPowers(0);
        updateTelemtry ();
    }

    public void turnTime(String direction, double time, double speed) {
        updateTelemtry();
        direction = direction.toLowerCase(Locale.ENGLISH);
        for(double startTime = getElaspedTime(timeStarted); (getElaspedTime(startTime) < time) && opModeIsActive();) {
            if (direction == "right") {
                this.robot.tank(speed, -speed);
            } else if (direction == "left") {
                this.robot.tank(-speed, speed);
            } else {
                telemetry.addLine("Error: expected a [left or right] in the direction for turnTime and did not find it.");
                telemetry.update();
            }
        }
        this.robot.setPowers(0);
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
            timeStarted = System.nanoTime()/nsPerS;
            updateTelemtry ();
            forwardTime(1, speed);
            turnTime("left", 1, speed);
            requestOpModeStop();
        }
    }
}
