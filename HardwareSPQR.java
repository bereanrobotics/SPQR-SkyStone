package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * This class defines all hardware on the robot.
 * Motor channel:  Left front drive motor:       "left_front_drive"
 * Motor channel:  Left back drive motor:        "left_back_drive"
 * Motor channel:  Right front drive motor:      "right_front_drive"
 * Motor channel:  Right back drive motor:       "right_back_drive"
 */
public class HardwareSPQR {

    //Hardware
    public DcMotor leftFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor rightBackDrive = null;
    public DcMotor leftIntake = null;
    public DcMotor rightIntake = null;

    //Hardware map
    HardwareMap hwMap = null;

    //Initializer
    public void init(HardwareMap ahwMap) {

        //Initialize hardware map
        this.hwMap = ahwMap;

        //Initializes motor
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


        //Set all motors to use encoders
        this.leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}