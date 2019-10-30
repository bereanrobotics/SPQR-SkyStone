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
 * Servo channel:  Left intake drop servo:       "left_drop"
 * Servo channel:  Right intake drop servo:      "right_drop"
 * Servo channel:  Block hitter servo:           "block_beater"
 */
public class HardwareSPQR {

    //Declare hardware
    public DcMotor leftFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor rightBackDrive = null;
    public DcMotor leftIntake = null;
    public DcMotor rightIntake = null;
    public Servo leftDrop = null;
    public Servo rightDrop = null;
    public Servo blockBeater = null;

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

        //Set all motor power to zero
        this.leftFrontDrive.setPower(0);
        this.leftBackDrive.setPower(0);
        this.rightFrontDrive.setPower(0);
        this.rightBackDrive.setPower(0);
        this.leftIntake.setPower(0);
        this.rightIntake.setPower(0);

        //Set all motors to use or not use encoders
        this.leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        /* Initialize servos */

        //Define servos
        this.leftDrop = this.hwMap.get(Servo.class, "left_drop");
        this.rightDrop = this.hwMap.get(Servo.class, "right_drop");
        this.blockBeater = this.hwMap.get(Servo.class, "block_beater");

        //Reset servo positions
        this.leftDrop.setPosition(1);
        this.rightDrop.setPosition(1);
        this.blockBeater.setPosition(1);
    }

    //Drop the intake
    public void drop(){
        this.leftDrop.setPosition(-1);
        this.rightDrop.setPosition(-1);
    }

    //Reset intake position
    public void resetIntakePosition(){
        this.leftDrop.setPosition(1);
        this.leftDrop.setPosition(1);
    }
}