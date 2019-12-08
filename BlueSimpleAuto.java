//package org.firstinspires.ftc.teamcode;
//
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//@Autonomous(name="Blue Simple Auto")
//public class BlueSimpleAuto extends SPQRLinearOpMode {
//    public long halfNinetyDegreeTime = 5000;
//
//    private double speed = -0.5;
//    private double turnSpeed = -1;
//    @Override
//    public void runOpMode() {
//        this.hardwareInit();
//
//        waitForStart();
//
//        if(opModeIsActive() && !isStopRequested()) {
//            this.driveForTime(500, speed);
//            this.turnForTime(Dir.LEFT, halfNinetyDegreeTime, turnSpeed);
//            this.turnForTime(Dir.RIGHT,halfNinetyDegreeTime, -turnSpeed);
//            this.driveForTime(8000, speed);
//            this.turnForTime(Dir.RIGHT,halfNinetyDegreeTime, turnSpeed);
//            this.turnForTime(Dir.LEFT,halfNinetyDegreeTime, -turnSpeed);
//            this.robot.tow.setPosition(1);
//            }
//        }
//    }
//
