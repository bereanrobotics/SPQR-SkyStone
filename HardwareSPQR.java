package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Hardware configurations
 *
 * Motor channel H1-0 (NeveRest 40 Gearmotor):        Left front drive motor:   "left_front_drive"
 * Motor channel H1-1 (NeveRest 40 Gearmotor):        Right front drive motor:  "right_front_drive"
 * Motor channel H1-2 (NeveRest 40 Gearmotor):        Left back drive motor:    "left_back_drive"
 * Motor channel H1-3 (NeveRest 40 Gearmotor):        Right back drive motor:   "right_back_drive"
 * Motor channel H2-0 (Tetrix Motor):                 Arm motor:                "arm_motor"
 * Servo channel H1-0 (Servo):                        Block grabber servo:      "block_grabber"
 * Servo channel H1-1 (Servo):                        Arm balancer servo:       "arm_balancer"
 * Servo channel H2-0 (Servo):                        Buildplate tow servo:     "tow"
 * I2C H1-0-0 (REV Expansion Hub IMU):                Hub connector:            "imu"
 * I2C H2-0-0 (REV Expansion Hub IMU):                Hub connector:            "imu 1"
 * I2C H2-1-0 (Rev Color Sensor v3):                  Line park sensor          "line_park_sensor"
 */

/**
 * This class defines all hardware on the robot. It also contains common abstractions to interact
 * with hardware.
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

    /**
     * This method initializes the hardware on the robot including sensors, servos, and motors.
     * This method should be updated whenever a hardware device is added or removed, or when a
     * device's settings need to be modified. This method should be called once.
     *
     * @param ahwMap The hardware map of the class instance of the callee to be assigned to the
     *               instance of hardware.
     */
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
        this.setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Set motors to brake
        this.setDriveZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Set tolerance
        ((DcMotorEx) this.leftFrontDrive).setTargetPositionTolerance(10);
        ((DcMotorEx) this.leftBackDrive).setTargetPositionTolerance(10);
        ((DcMotorEx) this.rightFrontDrive).setTargetPositionTolerance(10);
        ((DcMotorEx) this.rightBackDrive).setTargetPositionTolerance(10);

        //Sets motor direction
        this.leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        this.leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        this.rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        this.rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
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
        this.tow.setPosition(  1);

        /* Initialize sensors */

        //Define sensors
        this.lineParkSensor = hwMap.get(ColorSensor.class, "line_park_sensor");

        //Turn on servo LEDs
        this.lineParkSensor.enableLed(true);

        this.robotIsInitialized = true;
    }

    /**
     * This method returns the optimal position of the arm balancer servo based on the position of
     * the motor.
     *
     * @param encoderPosition The current position of the arm encoder.
     * @return A double between -1.0 and 1.0 which is the optimal position of the servo.
     */
    public static double getServoPosition(int encoderPosition){

        //The equation of the line of best fit in the form y=mx+b
        double optimalPosition =  -0.0006803 * encoderPosition + -0.1261;
        if (optimalPosition > 1){
            optimalPosition = 1;
        }
        if (optimalPosition < -1){
            optimalPosition = -1;
        }
        return optimalPosition;
    }

    /* Movement abstractions */

    /**
     * This method sets the powers of the motors in a way that will strafe the robot in a given
     * direction.
     *
     * @param direction A Dir enumeration which states which direction the robot will strafe.
     * @param power A double between -1.0 and 1.0 which represents the speed at which the robot
     *              is to strafe.
     */
    public void strafe(Dir direction, double power){
        if (!robotIsInitialized) return;
        this.leftFrontDrive.setPower((direction == Dir.LEFT) ? -power : power);
        this.leftBackDrive.setPower((direction == Dir.LEFT) ? power: -power);
        this.rightFrontDrive.setPower((direction == Dir.LEFT) ? power: -power);
        this.rightBackDrive.setPower((direction == Dir.LEFT) ? -power : power);
    }

    /**
     * This method sets all of the motors to specific given power.
     *
     * @param power A double between -1.0 and 1.0 which represents the speed that the motors will
     *              be set to.
     */
    public void setPowers(double power){
        if (!robotIsInitialized) return;
        this.leftFrontDrive.setPower(power);
        this.leftBackDrive.setPower(power);
        this.rightFrontDrive.setPower(power);
        this.rightBackDrive.setPower(power);
    }

    /**
     * This method makes the robot go at full speed backward by setting all of the motor powers to
     * -1.0.
     */
    public void backward(){
        if (!robotIsInitialized) return;
        this.setPowers(-1.0);
    }

    /**
     * This method makes the robot go at full speed forward by setting all of the motor powers to
     * 1.0.
     */
    public void forward(){
        if (!robotIsInitialized) return;
        this.setPowers(1.0);
    }

    /**
     * This method takes two powers and sets the left motors to a given power and a right motors to
     * a given power respectively.
     *
     * @param left A double between -1.0 and 1.0 which determines the speed that the left motors
     *             will be set to.
     * @param right A double between -1.0 and 1.0 which determines the speed that the right motors
     *              will be set to.
     */
    public void tank(double left, double right){
        if (!robotIsInitialized) return;
        this.leftFrontDrive.setPower(left);
        this.leftBackDrive.setPower(left);
        this.rightFrontDrive.setPower(right);
        this.rightBackDrive.setPower(right);
    }

    /**
     * This method stops the movement of the drive motors. If the motors are set to brake on zero
     * power the robot will stop in place.
     */
    public void stopMoving(){
        if (!this.robotIsInitialized) return;
        this.setPowers(0);
    }

    /* Arm movement */

    /**
     * This method closes the block-grabbing servo.
     */
    public void grabBlock(){
        if (!this.robotIsInitialized) return;
        this.blockGrabber.setPosition(-1);
    }

    /**
     * This method opens the block-grabbing servo.
     */
    public void releaseBlock(){
        if (!this.robotIsInitialized) return;
        this.blockGrabber.setPosition(1);
    }

    /**
     * This method sets the drive behavior of what one of the drive motors of the robot are to do
     * when its power is set to 0.
     *
     * @param behavior A ZeroPowerBehavior enumeration (Under DcMotor) which will be applied to all
     *                 of the drive motors of the robot.
     */
    public void setDriveZeroPowerBehavior (DcMotor.ZeroPowerBehavior behavior){
        this.leftFrontDrive.setZeroPowerBehavior(behavior);
        this.rightFrontDrive.setZeroPowerBehavior(behavior);
        this.leftBackDrive.setZeroPowerBehavior(behavior);
        this.rightBackDrive.setZeroPowerBehavior(behavior);
    }

    /**
     *
     * @param mode
     */
    public void setDriveMode (DcMotor.RunMode mode){
        this.leftFrontDrive.setMode(mode);
        this.rightFrontDrive.setMode(mode);
        this.leftBackDrive.setMode(mode);
        this.rightBackDrive.setMode(mode);
    }

    /**
     *
     * @param target
     */
    public void setDriveTargetPosition (int target) {
        this.leftFrontDrive.setTargetPosition(target);
        this.rightFrontDrive.setTargetPosition(target);
        this.leftBackDrive.setTargetPosition(target);
        this.rightBackDrive.setTargetPosition(target);
    }
}

