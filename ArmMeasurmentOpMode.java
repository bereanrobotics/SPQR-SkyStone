package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * TeleOp: PIT ONLY
 *
 * Gunner controls:
 *  - Left stick Y: Move arm balancer.
 *  - Right stick Y: Move arm.
 *
 * @author Arkin Solomon
 */
@TeleOp(name="Arm Measurment", group="Test")
public class ArmMeasurmentOpMode extends OpMode {

    private HardwareSPQR robot = new HardwareSPQR();

    @Override
    public void init() {

        //Initialize hardware
        this.robot.init(hardwareMap);
    }

    @Override
    public void loop(){

        /* Move arm */
        this.robot.armMotor.setPower(gamepad2.right_stick_y / 10);

        /* Move arm balancer */
        this.robot.armBalancer.setPosition(this.robot.armBalancer.getPosition() - gamepad2.left_stick_y / 100);

        /* Update telemetry */
        telemetry.addData("Arm encoder position", this.robot.armMotor.getCurrentPosition());
        telemetry.addData("Balancer servo position", this.robot.armBalancer.getPosition());
        telemetry.addData("Gamepad 2 left stick Y", gamepad2.left_stick_y);
        telemetry.addData("Gamepad 2 right stick Y", gamepad2.left_stick_y);
        telemetry.update();
    }
}
