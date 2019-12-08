package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Red Simple Auto")
public class RedSimpleAuto extends SPQRLinearOpMode {
    private boolean isInitialized = false;
    private HardwareSPQR robot = new HardwareSPQR();
    public long ninetyDegreeTime = 5000;

    private double speed = -0.5;
    private double turnSpeed = -1;
    @Override
    public void runOpMode() {
        if (!isInitialized) {
            isInitialized = true;
            this.hardwareInit();
//            this.robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        waitForStart();

        if(opModeIsActive() && !isStopRequested()) {
            this.driveForTime(speed, 500);
            this.turnForTime(Dir.RIGHT, turnSpeed, ninetyDegreeTime);
            this.driveForTime(speed, 8000);
            this.turnForTime(Dir.RIGHT,turnSpeed, ninetyDegreeTime);
            this.robot.tow.setPosition(1);
            this.driveForTime(-speed, 3000);
            this.turnForTime(Dir.LEFT, turnSpeed, ninetyDegreeTime);

            this.robot.setPowers(speed);
            if (true){
                this.robot.setPowers(0);
            }
        }
    }
}

