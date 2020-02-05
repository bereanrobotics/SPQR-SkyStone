package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Planner Autonomous")
public class PlannerAuto extends SPQRLinearOpMode {

    @Override
    public void runOpMode() {
        this.hardwareInit();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {

            //AUTO GENERATED CODE
            //TOKEN: UGxhbm5lckF1dG88PlBsYW5uZXIgQXV0b25vbW91cyM8JD4jNTYyL34/fi80NTcvfj9+LzEjPCQ+IzQ1Ny9+P34vNDU3L34/fi8xIzwkPiM0NTcvfj9+LzI5NS9+P34vMSM8JD4jNTQxL34/fi8yOTkvfj9+LzEjPCQ+IzUyNy9+P34vMTU1L34/fi8xIzwkPiM0NTYvfj9+LzE1Ni9+P34vMSM8JD4jNDU1L34/fi81My9+P34vMSM8JD4jNTMxL34/fi81NS9+P34vMSM8JD4jNTM5L34/fi8yOTgvfj9+LzE=
            this.drive(6400.8, 1);
            this.turn(270, 1.0);
            this.drive(9875.52, 1);
            this.turn(-92.72631099390627, 1.0);
            this.drive(5126.442426790727, 1);
            this.turn(98.27928202694308, 1.0);
            this.drive(8819.62909260928, 1);
            this.turn(-274.7460415779345, 1.0);
            this.drive(4328.5892744865505, 1);
            this.turn(269.7493227719783, 1.0);
            this.drive(6279.1759153570465, 1);
            this.turn(-92.06368798585562, 1.0);
            this.drive(4634.563932885164, 1);
            this.turn(-86.60696447044535, 1.0);
            this.drive(14821.305480314479, 1);
        }
    }
}