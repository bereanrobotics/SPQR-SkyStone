package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red -bot (1) Block (2) Foundation (3) Line")
public class RedBotBlockFoundationLine extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //AUTO GENERATED CODE
            //TOKEN: UmVkQm90QmxvY2tGb3VuZGF0aW9uTGluZTw+UmVkIC1ib3QgKDEpIEJsb2NrICgyKSBGb3VuZGF0aW9uICgzKSBMaW5lIzwkPiM1NjAvfj9+LzQ2Ni9+P34vMSM8JD4jNDU2L34/fi80NjYvfj9+LzEjPCQ+IzQ1NC9+P34vNTEvfj9+LzEjPCQ+IzU2Mi9+P34vNTAvfj9+LzEjPCQ+IzU2Mi9+P34vMjk3L34/fi8x
            this.drive(7000, 1);
            this.drive(900,0.5);
            this.robot.tow.setPosition(-1);
            this.sleep(1000);
            this.drive(-2500, -1);
            this.turn(-90, 1.0);
            this.drive(16000, 1.0);
            this.turn(90, 1.0);
            this.robot.tow.setPosition(1);
            this.strafe(Dir.RIGHT, 11000, 1);
            this.drive(2700, 1);
            this.robot.tow.setPosition(-1);
            this.sleep(100);
            this.sleep(800);
            this.drive(-9250, -0.5);
            this.robot.tow.setPosition(1);
            this.strafe(Dir.LEFT, 16750, 1.0);
            this.drive(3000, 1.0);
        }
    }
}