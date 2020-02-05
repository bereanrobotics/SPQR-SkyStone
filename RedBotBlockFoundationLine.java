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
            this.drive(6339.84, 1);
            this.sleep(3000);
            this.turn(270.2761221009455, 1.0);
            this.drive(25298.693781426737, 1);
            this.turn(-89.74562078427168, 1.0);
            this.drive(6583.962216173481, 1);
            this.turn(-90.5305013166738, 1.0);
            this.drive(15057.119999999999, 1);
            this.turn(-90.5305013166738, 1.0);
            this.drive(15057.119999999999, 1);
            this.turn(-90.5305013166738, 1.0);
        }
    }
}