package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * This OpMode will mainly be used in the telemetry portions of competitions
 */
@Autonomous(name="Test Autonomous (Blue)")
public class TestAutoBlue extends OpMode {

    //Hardware declaration
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor rightIntake = null;
    private DcMotor leftIntake = null;
    private Servo leftDrop = null;
    private Servo rightDrop = null;
    private ColorSensor lineParkSensor = null;

    //Color of the tape to stop at
    private int[] tapeColor = {540, 1540, 2430};

    //Difference allowed of change
    private int change = 150;

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

        /* Initialize color sensor */

        //Define color sensor
        this.lineParkSensor = hardwareMap.get(ColorSensor.class, "line_park_sensor");

        //Turn on led
        this.lineParkSensor.enableLed(true);
    }

    //Main opmode code
    @Override
    public void start() {

        //Drop intake
        this.leftDrop.setPosition(-1);
        this.rightDrop.setPosition(1);

        //Assuming robot is heading 270deg and moving under the bridge
        //Park over line

        boolean isOnLine = false;
        while (!isOnLine){
            this.forward(0.5);
            int[] r = this.plusOrMinus(this.lineParkSensor.red(), this.change);
            int[] g = this.plusOrMinus(this.lineParkSensor.green(), this.change);
            int[] b = this.plusOrMinus(this.lineParkSensor.blue(), this.change);
            telemetry.addData("Red", lineParkSensor.red());
            telemetry.addData("Green", lineParkSensor.green());
            telemetry.addData("Blue", lineParkSensor.blue());
            telemetry.update();
            isOnLine = (((this.tapeColor[0] > r[0]) && (this.tapeColor[0] < r[1])) && ((this.tapeColor[1] > g[0]) && (this.tapeColor[1] < g[1])) && ((this.tapeColor[2] > b[0]) && (this.tapeColor[2] < b[1])));
        }
        this.lineParkSensor.enableLed(false);
        this.stopMoving();
    }

    @Override
    public void loop(){

        telemetry.addData("Red", lineParkSensor.red());
        telemetry.addData("Green", lineParkSensor.green());
        telemetry.addData("Blue", lineParkSensor.blue());
        int[] r = this.plusOrMinus(this.lineParkSensor.red(), this.change);
        int[] g = this.plusOrMinus(this.lineParkSensor.green(), this.change);
        int[] b = this.plusOrMinus(this.lineParkSensor.blue(), this.change);
        telemetry.addData("r0", r[0]);
        telemetry.addData("r1", r[1]);
        telemetry.addData("g0", g[0]);
        telemetry.addData("g1", g[1]);
        telemetry.addData("b0", b[0]);
        telemetry.addData("b1", b[1]);
        telemetry.addData("True?", (((this.tapeColor[0] > r[0]) && (this.tapeColor[0] < r[1])) && ((this.tapeColor[1] > g[0]) && (this.tapeColor[1] < g[1])) && ((this.tapeColor[2] > b[0]) && (this.tapeColor[2] < b[1]))));
    }

    //Strafe abstraction
    private void strafe(Dir direction, double power){
        this.leftFrontDrive.setPower((direction == Dir.LEFT) ? -power : power);
        this.leftBackDrive.setPower((direction == Dir.LEFT) ? power : -power);
        this.rightFrontDrive.setPower((direction == Dir.LEFT) ? power : -power);
        this.rightBackDrive.setPower((direction == Dir.LEFT) ? -power  : power);
    }

    /* Forward, stop, and backward abstraction */
    private void forward(double power){
        this.leftFrontDrive.setPower(power);
        this.leftBackDrive.setPower(power);
        this.rightFrontDrive.setPower(power);
        this.rightBackDrive.setPower(power);
    }
    private void stopMoving(){
        this.leftFrontDrive.setPower(0);
        this.leftBackDrive.setPower(0);
        this.rightFrontDrive.setPower(0);
        this.rightBackDrive.setPower(0);
    }
    private void backward(double power){
        this.leftFrontDrive.setPower(-power);
        this.leftBackDrive.setPower(-power);
        this.rightFrontDrive.setPower(-power);
        this.rightBackDrive.setPower(-power);
    }

    //Returns an array plus or minus a change given of a value given
    private int[] plusOrMinus(int value, int change){
        return new int[] {value - change, value + change};
    }
}