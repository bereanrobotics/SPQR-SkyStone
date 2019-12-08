package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Locale;

import static com.qualcomm.robotcore.eventloop.opmode.LinearOpMode.*;
import static java.lang.Thread.sleep;

/**
 * This class defines all hardware on the robot. It also contains movement abstractions.
 *
 * Motor channel H1-0 (NeveRest 40 Gearmotor):           Left front drive motor:   "left_front_drive"
 * Motor channel H1-1 (NeveRest 40 Gearmotor):           Left back drive motor:    "left_back_drive"
 * Motor channel H1-2 (NeveRest 40 Gearmotor):           Right front drive motor:  "right_front_drive"
 * Motor channel H1-3 (NeveRest 40 Gearmotor):           Right back drive motor:   "right_back_drive"
 * Motor channel H2-0 (REV Robotics 20:1 HD Hex Motor):  Right intake motor:       "right_intake"
 * Motor channel H2-1 (REV Robotics 20:1 HD Hex Motor):  Left intake motor:        "left_intake"
 * Motor channel H2-2 (Tetrix Motor):                    Arm motor:                "arm_motor"
 * Servo channel H1-0 (Servo):                           Left intake drop servo:   "left_drop"
 * Servo channel H1-1 (Servo):                           Right intake drop servo:  "right_drop"
 * Servo channel H1-2 (Servo):                           Block hitter servo:       "block_beater"
 * Servo channel H1-3 (Servo):                           Block grabber servo:      "block_grabber"
 * Servo channel H1-4 (Servo):                           Arm balancer servo:       "arm_balancer"
 * Servo channel H1-5 (Servo):                           Ramp dropper servo:       "ramp_drop"
 * Servo channel H2-0 (Servo):                           Buildplate tow:           "tow"
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
    public DcMotor leftIntake = null;
    public DcMotor rightIntake = null;
    public DcMotor armMotor = null;
    public Servo leftDrop = null;
    public Servo rightDrop = null;
    public Servo blockBeater = null;
    public Servo blockGrabber = null;
    public Servo armBalancer = null;
    public Servo rampDrop = null;
    public Servo tow = null;
    public ColorSensor lineParkSensor = null;

    //Hardware map
    HardwareMap hwMap = null;

    //True if robot is initialized
    private boolean robotIsInitialized = false;

    //Levels of blocks
    public static int[] levels = {-830, -1050, -1270};

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
        this.leftIntake = hwMap.get(DcMotor.class, "left_intake");
        this.rightIntake = hwMap.get(DcMotor.class, "right_intake");
        this.armMotor = hwMap.get(DcMotor.class, "arm_motor");

        //Set all motors to use or not use encoders
        this.leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //Reset encoder position to 0
        this.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Sets motor direction
        this.leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        this.leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        this.rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        this.rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        this.leftIntake.setDirection(DcMotor.Direction.FORWARD);
        this.rightIntake.setDirection(DcMotor.Direction.REVERSE);
        this.armMotor.setDirection(DcMotor.Direction.FORWARD);

        //Set all motor power to zero
        this.leftFrontDrive.setPower(0);
        this.leftBackDrive.setPower(0);
        this.rightFrontDrive.setPower(0);
        this.rightBackDrive.setPower(0);
        this.leftIntake.setPower(0);
        this.rightIntake.setPower(0);
        this.armMotor.setPower(0);

        /* Initialize servos */

        //Define servos
        this.leftDrop = hwMap.get(Servo.class, "left_drop");
        this.rightDrop = hwMap.get(Servo.class, "right_drop");
        this.blockBeater = hwMap.get(Servo.class, "block_beater");
        this.blockGrabber = hwMap.get(Servo.class, "block_grabber");
        this.armBalancer = hwMap.get(Servo.class, "arm_balancer");
        this.rampDrop = hwMap.get(Servo.class, "ramp_drop");
        this.tow = hwMap.get(Servo.class, "tow");

        //Reset servo positions
        this.leftDrop.setPosition(-1);
        this.rightDrop.setPosition(1);
        this.blockBeater.setPosition(1);
        this.rampDrop.setPosition(-1);
        this.blockGrabber.setPosition(1);
        this.armBalancer.setPosition(-1);
        this.tow.setPosition(-1);

        /* Initialize sensors */

        //Define sensors
        this.lineParkSensor = hwMap.get(ColorSensor.class, "line_park_sensor");

        //Turn on servo LEDs
        this.lineParkSensor.enableLed(true);

        robotIsInitialized = true;
    }

    /* Movement abstractions */

    //Strafe in a direction with a certain power
    public void strafe(Dir direction, double power){
        if (!robotIsInitialized) return;
        this.leftFrontDrive.setPower((direction == Dir.LEFT) ? -power : power);
        this.leftBackDrive.setPower((direction == Dir.LEFT) ? power : -power);
        this.rightFrontDrive.setPower((direction == Dir.LEFT) ? power : -power);
        this.rightBackDrive.setPower((direction == Dir.LEFT) ? -power  : power);
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

    /* Intake movement */

    //Speed of the intake
    private double intakeSpeed = 0.30;

    //Suck in blocks
    public void intakeIn(){
        this.leftIntake.setPower(this.intakeSpeed);
        this.rightIntake.setPower(this.intakeSpeed);
    }

    //Push out blocks
    public void intakeOut(){
        this.leftIntake.setPower(-this.intakeSpeed);
        this.rightIntake.setPower(-this.intakeSpeed);
    }

    //Stop intake
    public void stopIntake(){
        this.leftIntake.setPower(0);
        this.rightIntake.setPower(0);


    }

    public void forwardTime(long time, double speed) { //go forward for
            this.setPowers(speed);
            sleep(time);
            this.setPowers(0);
        }

    public void turnTime(String direction, long time, double speed) {
        direction = direction.toLowerCase(Locale.ENGLISH);
        if (direction == "right") {
            this.tank(speed, -speed);
        } else if (direction == "left") {
            this.tank(-speed, speed);
        }
        sleep(time);
        this.setPowers(0);
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /* Arm movement */

    //Drop the arm down to levels
    public void moveArm(int level, double speed){
        this.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.armMotor.setTargetPosition(this.levels[level]);
        this.armMotor.setPower(speed);
        while (this.armMotor.isBusy()){
            this.armMotor.setPower(0);
        }
    }

    //Grab a block
    public void grabBlock(){
        this.blockGrabber.setPosition(-1);
    }

    //Release a block
    public void releaseBlock(){
        this.blockGrabber.setPosition(1);
    }
}


