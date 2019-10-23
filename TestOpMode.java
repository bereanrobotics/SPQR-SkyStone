//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
///**
// * This OpMode will mainly be used in the telemetry portions of competitions
// */
//@TeleOp(name="Main OpMode")
//public class TestOpMode extends OpMode {
//
//    //Hardware
//    private DcMotor leftFrontDrive = null;
//    private DcMotor leftBackDrive = null;
//    private DcMotor rightFrontDrive = null;
//    private DcMotor rightBackDrive = null;
//
//    //Speed of the robot
//    private double speed = 1.0;
//
//    @Override
//    public void init() {
//
//        //Initializes motor
//        this.leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
//        this.leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
//        this.rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
//        this.rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
//
//        //Sets motor direction
//        this.leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
//        this.leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
//        this.rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
//        this.rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
//    }
//
//    @Override
//    public void loop() {
//
//        /* Left and right bumper movement */
//        if (gamepad1.left_bumper){
//            if (gamepad1.right_bumper) return;
//            this.strafe(Dir.LEFT, 1.0);
//        }
//        if (gamepad1.right_bumper) {
//            if (gamepad1.left_bumper) return;
//            this.strafe(Dir.RIGHT, 1.0);
//        }
//
//        /* Tank movement */
//        double right = -gamepad1.left_stick_y * this.speed;
//        double left = -gamepad1.right_stick_y * this.speed;
//
//        this.leftFrontDrive.setPower(left);
//        this.leftBackDrive.setPower(left);
//        this.rightFrontDrive.setPower(right);
//        this.rightBackDrive.setPower(right);
//
//        /* Left and right joystick movement */
//        double leftPower = gamepad1.left_stick_x;
//        double rightPower = gamepad1.right_stick_x;
//
//        if ((leftPower >= 0.08 || leftPower <= -0.08) && (rightPower >= 0.08 || rightPower <= -0.08)){
//            double power = (leftPower + rightPower) / 2;
//            if (leftPower < 0){
//                this.strafe(Dir.LEFT, power);
//            } else {
//                this.strafe(Dir.RIGHT, power);
//            }
//        }
//
//        /* Reverse direction */
//        if (gamepad1.a) {
//            this.speed = -this.speed;
//        }
//
//        /* Sniper mode */
//        if (gamepad1.b) {
//            this.speed = (this.speed > 0.5) ? 0.5 : 1.0;
//        }
//
//        /* Telementry data */
//        telemetry.addData("Gamepad 1 left Y", gamepad1.left_stick_y);
//        telemetry.addData("Gamepad 1 left X", gamepad1.left_stick_x);
//        telemetry.addData("Gamepad 1 right Y", gamepad1.right_stick_y);
//        telemetry.addData("Gamepad 1 right X", gamepad1.right_stick_x);
//    }
//
//    /* Strafe abstraction */
//    private void strafe(Dir direction, double power){
//        power *= this.speed;
//        this.leftFrontDrive.setPower((direction == Dir.LEFT) ? -power : power);
//        this.leftBackDrive.setPower((direction == Dir.LEFT) ? power : -power);
//        this.rightFrontDrive.setPower((direction == Dir.LEFT) ? power : -power);
//        this.rightBackDrive.setPower((direction == Dir.LEFT) ? -power : power);
//    }
//}
//
//enum Dir {
//    LEFT, RIGHT;
//}
