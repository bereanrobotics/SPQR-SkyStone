package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This class defines all hardware on the robot.
 * Motor channel:  Left front drive motor:       "left_front_drive"
 * Motor channel:  Left back drive motor:        "left_back_drive"
 * Motor channel:  Right front drive motor:      "right_front_drive"
 * Motor channel:  Right back drive motor:       "right_back_drive"
 * Motor channel:  Left intake motor:            "left_intake"
 * Motor channel:  Right intake motor:           "right_intake"
 * Motor channel:  Arm motor:                    "arm_motor"
 * Servo channel:  Left intake drop servo:       "left_drop"
 * Servo channel:  Right intake drop servo:      "right_drop"
 * Servo channel:  Block hitter servo:           "block_beater"
 * Servo channel:  Block grabber servo:          "block_grabber"
 * Servo channel:  Arm balancer servo:           "arm_balancer"
 */
public class HardwareSPQR {

    //Declare hardware
    public DcMotor leftFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor rightBackDrive = null;
    public DcMotor leftIntake = null;
    public DcMotor rightIntake = null;
    private DcMotor armMotor = null;
    public Servo leftDrop = null;
    public Servo rightDrop = null;
    public Servo blockBeater = null;
    public Servo blockGrabber = null;
    public Servo armBalancer = null;

    //Hardware map
    HardwareMap hwMap = null;

    //Initializer
    public void init(HardwareMap ahwMap) {

        //Initialize hardware map
        this.hwMap = ahwMap;

        /* Initialize motors*/

        //Define motors
        this.leftFrontDrive = this.hwMap.get(DcMotor.class, "left_front_drive");
        this.leftBackDrive = this.hwMap.get(DcMotor.class, "left_back_drive");
        this.rightFrontDrive = this.hwMap.get(DcMotor.class, "right_front_drive");
        this.rightBackDrive = this.hwMap.get(DcMotor.class, "right_back_drive");
        this.leftIntake = this.hwMap.get(DcMotor.class, "left_intake");
        this.rightIntake = this.hwMap.get(DcMotor.class, "right_intake");
        this.armMotor = this.hwMap.get(DcMotor.class, "arm_motor");

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
        this.leftIntake.setDirection(DcMotor.Direction.REVERSE);
        this.rightIntake.setDirection(DcMotor.Direction.FORWARD);
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
        this.leftDrop = this.hwMap.get(Servo.class, "left_drop");
        this.rightDrop = this.hwMap.get(Servo.class, "right_drop");
        this.blockBeater = this.hwMap.get(Servo.class, "block_beater");
        this.blockGrabber = hardwareMap.get(Servo.class, "block_grabber");
        this.armBalancer = hardwareMap.get(Servo.class, "arm_balancer");

        //Reset servo positions
        this.leftDrop.setPosition(1);
        this.rightDrop.setPosition(1);
        this.blockBeater.setPosition(1);
    }
}
