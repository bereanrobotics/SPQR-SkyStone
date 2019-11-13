package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Dir;

/**
 * This OpMode will mainly be used in the telemetry portions of competitions
 */
@TeleOp(name="Main OpMode")
public class MainOpMode extends OpMode {

    //Hardware declaration
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor rightIntake = null;
    private DcMotor leftIntake = null;
    private DcMotor armMotor = null;
    private Servo blockBeater = null;

    //Speed of the robot
    private double speed = 1.0;

    @Override
    public void init() {

        /* Initialize motors*/

        //Define motors
        this.leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        this.leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        this.rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        this.rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        this.leftIntake = hardwareMap.get(DcMotor.class, "left_intake");
        this.rightIntake = hardwareMap.get(DcMotor.class, "right_intake");
        this.armMotor = hardwareMap.get(DcMotor.class, "arm_motor");

        //Set all motors to use or not use encoders
        this.leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Sets motor direction
        this.leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        this.leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        this.rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        this.rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        this.leftIntake.setDirection(DcMotor.Direction.REVERSE);
        this.rightIntake.setDirection(DcMotor.Direction.FORWARD);
        this.armMotor.setDirection(DcMotor.Direction.FORWARD);

        /* Initialize servos */

        //Define servos
        this.blockBeater = hardwareMap.get(Servo.class, "block_beater");

        //Reset servo positions
        this.blockBeater.setPosition(1);
    }

    @Override
    public void loop() {

        /* Left and right bumper movement */
        if (gamepad1.left_bumper){
            if (gamepad1.right_bumper) return;
            this.strafe(Dir.LEFT, 1.0);
        }
        if (gamepad1.right_bumper) {
            if (gamepad1.left_bumper) return;
            this.strafe(Dir.RIGHT, 1.0);
        }

        /* Tank movement */
        double right = -gamepad1.left_stick_y * this.speed;
        double left = -gamepad1.right_stick_y * this.speed;

        this.leftFrontDrive.setPower(left);
        this.leftBackDrive.setPower(left);
        this.rightFrontDrive.setPower(right);
        this.rightBackDrive.setPower(right);

        /* Left and right joystick movement */
        double leftPower = gamepad1.left_stick_x;
        double rightPower = gamepad1.right_stick_x;

        if ((leftPower >= 0.08 || leftPower <= -0.08) && (rightPower >= 0.08 || rightPower <= -0.08)){
            double power = (leftPower + rightPower) / 2;
            if (leftPower < 0){
                this.strafe(Dir.LEFT, power);
            } else {
                this.strafe(Dir.RIGHT, power);
            }
        }

        /* Reverse direction */
        if (gamepad1.a) {
            this.speed = -this.speed;
        }

        /* Sniper mode */
        if (gamepad1.b) {
            this.speed = (this.speed > 0.5) ? 0.5 : 1.0;
        }

        /* Move block beater */
        if (gamepad2.dpad_up) {
            this.blockBeater.setPosition(-1);
        }
        if (gamepad2.dpad_down){
            this.blockBeater.setPosition(1);
        }

        /* Intake */
        if (gamepad2.left_trigger > 0.1){ //Left trigger first so that right trigger overrides
            leftIntake.setPower(-1);
            rightIntake.setPower(-1);
        } else {
            leftIntake.setPower(0);
            rightIntake.setPower(0);
        }
        if (gamepad2.right_trigger > 0.1){
            leftIntake.setPower(1);
            rightIntake.setPower(1);
        } else {
            leftIntake.setPower(0);
            rightIntake.setPower(0);
        }

        /* Arm movement */
        armMotor.setPower(-gamepad2.right_stick_y / 4);

        /* Telementry data */
        telemetry.addData("Arm", this.armMotor.getCurrentPosition());
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
