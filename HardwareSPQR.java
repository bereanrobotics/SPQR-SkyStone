package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This class defines all hardware on the robot. It also contains movement abstractions.
 *
 * Motor channel H1-0 (NeveRest 40 Gearmotor):           Left front drive motor:   "left_front_drive"
 * Motor channel H1-1 (NeveRest 40 Gearmotor):           Right front drive motor:  "right_front_drive"
 * Motor channel H1-2 (NeveRest 40 Gearmotor):           Left back drive motor:    "left_back_drive"
 * Motor channel H1-3 (NeveRest 40 Gearmotor):           Right back drive motor:   "right_back_drive"
 * Motor channel H2-0 (Tetrix Motor):                    Arm motor:                "arm_motor"
 * Servo channel H1-0 (Servo):                           Block grabber servo:      "block_grabber"
 * Servo channel H1-1 (Servo):                           Arm balancer servo:       "arm_balancer"
 * Servo channel H2-0 (Servo):                           Buildplate tow servo:     "tow"
 * I2C H1-0-0 (REV Expansion Hub IMU):                   Hub connector:            "imu"
 * I2C H2-0-0 (REV Expansion Hub IMU):                   Hub connector:            "imu 1"
 * I2C H2-1-0 (Rev Color Sensor v3):                     Line park sensor          "line_park_sensor"
 *
 * @author Arkin Solomon
 */
public class HardwareSPQR {

    //Declare hardware
    public DcMotor leftFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor rightBackDrive = null;
    public DcMotor armMotor = null;
    public Servo blockGrabber = null;
    public Servo armBalancer = null;
    public Servo tow = null;
    public ColorSensor lineParkSensor = null;

    //Hardware map
    HardwareMap hwMap = null;

    //True if robot is initialized
    private boolean robotIsInitialized = false;

    //Initializer
    public void init(HardwareMap ahwMap) {

        //Initialize hardware map
        hwMap = ahwMap;

        /* Initialize motors*/

        //Define motors
        this.leftFrontDrive = hwMap.get(DcMotor.class, "left_front_drive");
        this.leftBackDrive = hwMap.get(DcMotor.class, "left_back_drive");
        this.rightFrontDrive = hwMap.get(DcMotor.class, "right_front_drive");
        this.rightBackDrive = hwMap.get(DcMotor.class, "right_back_drive");
        this.armMotor = hwMap.get(DcMotor.class, "arm_motor");


        //Reset encoders
        this.leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        

        //Sets motor direction
        this.leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        this.leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        this.rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        this.rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        this.armMotor.setDirection(DcMotor.Direction.FORWARD);

        //Set all motor power to zero
        this.leftFrontDrive.setPower(0);
        this.leftBackDrive.setPower(0);
        this.rightFrontDrive.setPower(0);
        this.rightBackDrive.setPower(0);
        this.armMotor.setPower(0);

        /* Initialize servos */

        //Define servos
        this.blockGrabber = hwMap.get(Servo.class, "block_grabber");
        this.armBalancer = hwMap.get(Servo.class, "arm_balancer");
        this.tow = hwMap.get(Servo.class, "tow");

        //Reset servo positions
        this.blockGrabber.setPosition(1);
        this.armBalancer.setPosition(0);
        this.tow.setPosition(-1);

        /* Initialize sensors */

        //Define sensors
        this.lineParkSensor = hwMap.get(ColorSensor.class, "line_park_sensor");

        //Turn on servo LEDs
        this.lineParkSensor.enableLed(true);

        robotIsInitialized = true;
    }

    //Function to get the ideal position of a square
    public static double getServoPosition(int encoderPosition){
      return -0.0006803 * encoderPosition + -0.1261;
    }

    /* Movement abstractions */

    //Strafe in a direction with a certain power
    public void strafe(Dir direction, double power){
        if (!robotIsInitialized) return;
        this.leftFrontDrive.setPower((direction == Dir.LEFT) ? -power : power);
        this.leftBackDrive.setPower((direction == Dir.LEFT) ? power: -power);
        this.rightFrontDrive.setPower((direction == Dir.LEFT) ? power: -power);
        this.rightBackDrive.setPower((direction == Dir.LEFT) ? -power : power);
    }

    //Set all motors to certain power
    public void setPowers(double power){
        if (!robotIsInitialized) return;
        this.leftFrontDrive.setPower(power);
        this.leftBackDrive.setPower(power);
        this.rightFrontDrive.setPower(power);
        this.rightBackDrive.setPower(power);
    }

    //Go backwards at full speed
    public void backward(){
        if (!robotIsInitialized) return;
        this.setPowers(-1.0);
    }

    //Go forward at full speed
    public void forward(){
        if (!robotIsInitialized) return;
        this.setPowers(1.0);
    }

    //Set left and right motors differently
    public void tank(double left, double right){
        if (!robotIsInitialized) return;
        this.leftFrontDrive.setPower(left);
        this.leftBackDrive.setPower(left);
        this.rightFrontDrive.setPower(right);
        this.rightBackDrive.setPower(right);
    }

    //Stop moving
    public void stopMoving(){
        if (!this.robotIsInitialized) return;
        this.setPowers(0);
    }

    /* Intake movement */

    //Speed of the intake
    private double intakeSpeed = 0.30;

    /* Arm movement */

    //Grab a block
    public void grabBlock(){
        if (!this.robotIsInitialized) return;
        this.blockGrabber.setPosition(-1);
    }

    //Release a block
    public void releaseBlock(){
        if (!this.robotIsInitialized) return;
        this.blockGrabber.setPosition(1);
    }
}
