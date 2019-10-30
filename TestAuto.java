package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Dir;

/**
 * This OpMode will mainly be used in the telemetry portions of competitions
 */
@Autonomous(name="Test Autonomous")
public class TestAuto extends OpMode {

    //Hardware declaration
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor rightIntake = null;
    private DcMotor leftIntake = null;
    private Servo leftDrop = null;
    private Servo rightDrop = null;

    //Speed of the robot
    private double speed = 1.0;

    @Override
    public void init() {

        /* Initialize motors */

        //Define motors
        this.leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        this.leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        this.rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        this.rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        this.leftIntake = hardwareMap.get(DcMotor.class, "left_intake");
        this.rightIntake = hardwareMap.get(DcMotor.class, "right_intake");

        //Sets motor direction
        this.leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        this.leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        this.rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        this.rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        this.leftIntake.setDirection(DcMotor.Direction.REVERSE);
        this.rightIntake.setDirection(DcMotor.Direction.FORWARD);

        /* Initialize servos */

        //Define servos
        this.leftDrop = hardwareMap.get(Servo.class, "left_drop");
        this.rightDrop = hardwareMap.get(Servo.class, "right_drop");

        //Reset servo positions
        this.leftDrop.setPosition(1);
        this.rightDrop.setPosition(-1);
    }

    //Runs once
    @Override
    public void start() {

        //Drop intake
        this.leftDrop.setPosition(-1);
        this.rightDrop.setPosition(1);
    }

    @Override
    public void loop() {

    }

    /* Strafe abstraction */
    private void strafe(Dir direction, double power){
        power *= this.speed;
        this.leftFrontDrive.setPower((direction == Dir.LEFT) ? -power : power);
        this.leftBackDrive.setPower((direction == Dir.LEFT) ? power : -power);
        this.rightFrontDrive.setPower((direction == Dir.LEFT) ? power : -power);
        this.rightBackDrive.setPower((direction == Dir.LEFT) ? -power  : power);
    }
}