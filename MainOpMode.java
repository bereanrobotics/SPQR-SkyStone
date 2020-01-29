package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.Dir;

/**
 * This OpMode will mainly be used in the telemetry portions of competitions
 *
 * @author Arkin Solomon
 */
@TeleOp(name="Main OpMode")
public class MainOpMode extends OpMode {

    private HardwareSPQR robot = new HardwareSPQR();

    //Speed of the robot
    private double speed = 1.0;

    //Prevent detecting multiple clicks
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
            this.robot.strafe(Dir.LEFT, this.speed);
        }
        if (gamepad1.right_bumper) {
            if (gamepad1.left_bumper) return;
            this.robot.strafe(Dir.RIGHT, this.speed);
        }

        /* Tank movement */ 
        double right = -gamepad1.right_stick_y * this.speed;
        double left = -gamepad1.left_stick_y * this.speed;
        if (this.speed < 0){
            double l = left;
            left = right;
            right = l;
        }

        if (!gamepad1.left_bumper && !gamepad1.right_bumper){
            this.robot.tank(right, left);
        }

        /* Reverse direction */
        if (gamepad1.a) {
            if (this.gamepad1_aPressed) return;
            this.gamepad1_aPressed = true;
            this.speed *= -1;
        }
        if (!gamepad1.a){
            this.gamepad1_aPressed = false;
        }

        /* Sniper mode */
        if (gamepad1.b) {
            if (this.gamepad1_bPressed) return;
            this.gamepad1_bPressed = true;
            this.speed = (this.speed > 0.5) ? 0.5 : 1.0;
        }
        if (!gamepad1.b){
            this.gamepad1_bPressed = false;
        }

        /* Bring tow down */
        if (gamepad1.dpad_down){
            this.robot.tow.setPosition(-1);
        }
        if (gamepad1.dpad_up){
            this.robot.tow.setPosition(1);
        }

        /* Grab blocks */
        if (gamepad2.a){
            this.robot.grabBlock();
        }
        if (gamepad2.b){
            this.robot.releaseBlock();
        }

        /* Arm movement */

        //Reset arm zero
        if (gamepad2.right_bumper){
            this.robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        this.robot.armMotor.setPower(gamepad2.right_stick_y / 10);
        this.robot.armBalancer.setPosition(this.robot.getServoPosition(this.robot.armMotor.getCurrentPosition()));

        /* Telementry data */
        telemetry.addData("Arm", this.robot.armMotor.getCurrentPosition());
        telemetry.addData("Servo", this.robot.armBalancer.getPosition());
        telemetry.addData("Red", this.robot.lineParkSensor.red());
        telemetry.addData("Green", this.robot.lineParkSensor.green());
        telemetry.addData("Blue", this.robot.lineParkSensor.blue());
    }
}
