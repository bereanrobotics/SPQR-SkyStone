package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.System.*;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.pow;

@Autonomous(name="Blue Simple Auto")
public class BlueSimpleAuto extends SPQRLinearOpMode {
    private boolean isInitialized = false;
    private HardwareSPQR robot = new HardwareSPQR();
    public long halfNinetyDegreeTime = 5000;

    private double speed = -0.5;
    private double turnSpeed = -1;
    @Override
    public void runOpMode() {
        if (!isInitialized) {
            isInitialized = true;
            this.hardwareInit();
//            this.robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        waitForStart();

        if(opModeIsActive() && !isStopRequested()) {
            this.robot.forwardTime(500, speed);
            this.robot.turnTime("left", halfNinetyDegreeTime, turnSpeed);
            this.robot.turnTime("right",halfNinetyDegreeTime, -turnSpeed);
            this.robot.forwardTime(8000, speed);
            this.robot.turnTime("right",halfNinetyDegreeTime, turnSpeed);
            this.robot.turnTime("left",halfNinetyDegreeTime, -turnSpeed);
            this.robot.tow.setPosition(1);
            }
        }
    }

