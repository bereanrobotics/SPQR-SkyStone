package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.Dir;

/**
 * This OpMode will mainly be used in the telemetry portions of competitions
 * @author Arkin Solomon
 */
@TeleOp(name="Main OpMode")
public class MainOpMode extends OpMode {

    private HardwareSPQR robot = new HardwareSPQR();

    //Speed of the robot
    private double speed = -1.0;

    //Level counter
    private int levelCounter = 0;

    //If we are locking to levels
    private boolean isArmLocked = false;

    //Prevent detecting multiple clicks
    private boolean dpad_upPressed = false;
    private boolean dpad_downPressed = false;
    private boolean gamepad1_aPressed = false;
    private boolean gamepad1_bPressed = false;

    @Override
    public void init() {

        //Initialize hardware
        this.robot.init(hardwareMap);
    }

    @Override
    public void loop() {

        /* Left and right bumper movement */
        if (gamepad1.left_bumper){
            if (gamepad1.right_bumper) return;
            this.robot.strafe(Dir.LEFT, 1.0);
        }
        if (gamepad1.right_bumper) {
            if (gamepad1.left_bumper) return;
            this.robot.strafe(Dir.RIGHT, 1.0);
        }

        /* Tank movement */
        double right = -gamepad1.left_stick_y * this.speed;
        double left = -gamepad1.right_stick_y * this.speed;
        if (this.speed < 0){
            double l = left;
            left = right;
            right = l;
        }

        this.robot.tank(left, right);

        /* Reverse direction */
        if (gamepad1.a) {
            if (this.gamepad1_aPressed) return;
            this.gamepad1_aPressed = true;
            this.speed = -this.speed;
        }
        if (!gamepad1.a){
            this.gamepad1_aPressed = false;
        }

        /* Sniper mode */
        if (gamepad1.b) {
            this.speed = (this.speed > 0.5) ? 0.5 : 1.0;
        }

        /* Move block beater */
        if (gamepad2.dpad_left) {
            this.robot.blockBeater.setPosition(-1);
        }
        if (gamepad2.dpad_right){
            this.robot.blockBeater.setPosition(1);
        }

        /* Bring tow down */
        if (gamepad1.dpad_down){
            this.robot.tow.setPosition(-1);
        }
        if (gamepad1.dpad_up){
            this.robot.tow.setPosition(1);
        }

        /* Intake */
        if (gamepad2.right_trigger > 0.1){
            this.robot.intakeIn();
        } else if (gamepad2.left_trigger > 0.1){
            this.robot.intakeOut();
        } else {
            this.robot.stopIntake();
        }

        /* Grab blocks */
        if (gamepad2.a){
            this.robot.blockGrabber.setPosition(-1);
        }
        if (gamepad2.b){
            this.robot.blockGrabber.setPosition(1);
        }

        /* Arm movement */

        //Reset arm zero
        if (gamepad2.right_bumper){
            DcMotor.RunMode previousMode = this.robot.armMotor.getMode();
            this.robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.robot.armMotor.setMode(previousMode);
        }

        //Toggle arm lock or unlock
        if (gamepad2.x){
            this.isArmLocked = false;
        }
        if (gamepad2.y){
            this.isArmLocked = true;
        }

        //Change levels up or down
        if (gamepad2.dpad_up){
            if (this.dpad_upPressed) return;
            this.dpad_upPressed = true;
            this.levelCounter += (this.levelCounter < this.robot.levels.length - 1) ? 1 : 0;
        }
        if (gamepad2.dpad_down){
            if (this.dpad_downPressed) return;
            this.dpad_downPressed = true;
            this.levelCounter -= (this.levelCounter > 0) ? 1 : 0;
        }
        if (!gamepad2.dpad_up){
            this.dpad_upPressed = false;
        }
        if (!gamepad2.dpad_down){
            this.dpad_downPressed = false;
        }


        //Move to level
        if (this.isArmLocked){
            this.robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            this.robot.armMotor.setTargetPosition(this.robot.levels[this.levelCounter]);
            this.robot.armBalancer.setPosition(this.robot.servoLevels[this.levelCounter]);
            if (this.robot.armMotor.isBusy()){
                this.robot.armMotor.setPower(0.35);
            }else{
                this.robot.armMotor.setPower(0);
            }
        }else{

            //Adjust minutely
            this.robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.robot.armMotor.setPower(gamepad2.right_stick_y / 10);
        }

        /* Telementry data */
        telemetry.addData("Arm", this.robot.armMotor.getCurrentPosition());
        telemetry.addData("Level", this.levelCounter);
        telemetry.addData("Servo", this.robot.armBalancer.getPosition());
        telemetry.addData("Red", this.robot.lineParkSensor.red());
        telemetry.addData("Green", this.robot.lineParkSensor.green());
        telemetry.addData("Blue", this.robot.lineParkSensor.blue());
    }
}
