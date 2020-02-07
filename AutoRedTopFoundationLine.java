package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Sunflower Red -top (1) Foundation (2) Line -in", group="Red")
public class AutoRedTopFoundationLine extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //AUTO GENERATED CODE
            //TOKEN: QXV0b1JlZFRvcEZvdW5kYXRpb25MaW5lPD5SZWQgLXRvcCAoMSkgRm91bmRhdGlvbiAoMikgTGluZSM8JD4jNTYwL34/fi8xMzcvfj9+LzEjPCQ+IzQ2Ny9+P34vMTM4L34/fi8xIzwkPiM0NjUvfj9+LzQ0L34/fi8xIzwkPiM0MTQvfj9+LzQ0L34/fi8xIzwkPiM1NTAvfj9+LzQ0L34/fi8xIzwkPiM1NTIvfj9+LzMwNS9+P34vMQ==
            this.drive(5500, 1);
            this.strafe(Dir.RIGHT, 5600, 1);
            this.drive(2600, 1);
            this.robot.tow.setPosition(-1);
            this.sleep(1000);
            this.drive(-8200, -0.5);
            this.robot.tow.setPosition(1);
            this.strafe(Dir.LEFT, 18000, 1.0);
            this.drive(-2000, -0.5);
        }
    }
}
