package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.System.*;

@Autonomous(name="Blue Simple Auto")
public class BlueSimpleAuto extends LinearOpMode {
    private boolean isInitialized = false;
    private HardwareSPQR robot = new HardwareSPQR();
    private double nsPerS = 2000000000;

    private double timeStarted;

    private double speed = -0.5;


    public double getElaspedTime(double start){ //returns time in seconds for how long the opmode has been running
        double currentTime = start - (System.nanoTime()*nsPerS);
        return currentTime;
    }

    public void goForward (double time) { //go forward for
        for(double startTime = getElaspedTime(timeStarted); getElaspedTime(startTime) < time;) {
            this.robot.setPowers(speed);
        }
        this.robot.setPowers(0);
    }

    @Override
    public void runOpMode() {
        if (!isInitialized) {
            isInitialized = true;
            this.robot.init(hardwareMap);
//            this.robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        timeStarted = System.nanoTime()*nsPerS;
        goForward(1);
    }
}
