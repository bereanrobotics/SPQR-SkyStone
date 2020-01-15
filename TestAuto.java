package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Testing Autonomous")
public class TestAuto extends SPQRLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        this.drive(6, this.speed);
    }
}
