//package org.firstinspires.ftc.teamcode;
//
////Basically, create a program that uses the Vuforia input to get to a defined current posistion and rotation
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import static org.firstinspires.ftc.teamcode.Constants.mmPerInch;
//
//import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
//import org.firstinspires.ftc.teamcode.VuforiaSkyStoneNavigation.lastLocation;
//
//@Autonomous(name="Test: Position Seeking by Vuforia", group="Pushbot")
//public class AutonomousVuforiaTest extends VuforiaSkyStoneNavigation {
//
//    public void VuforiaPosistion(double speed,
//                             double leftInches, double rightInches,
//                             double timeoutS, double TargetX, double TargetY, double TargetZ ) {
//double TargetXmm = TargetX * mmPerInch;
//double TargetYmm = TargetY * mmPerInch;
//double TargetZmm = TargetZ * mmPerInch;
//OpenGLMatrix LASTLOCATION = LastLocation;
//    }
//        int newLeftTarget;
//        int newRightTarget;
//
//        // Ensure that the opmode is still active
//        if (opModeIsActive()) {
//
//            // Determine new target position, and pass to motor controller
//            newLeftTarget = robot.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
//            newRightTarget = robot.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
//            robot.leftDrive.setTargetPosition(newLeftTarget);
//            robot.rightDrive.setTargetPosition(newRightTarget);
//
//            // Turn On RUN_TO_POSITION
//            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            // reset the timeout time and start motion.
//            runtime.reset();
//            robot.leftDrive.setPower(Math.abs(speed));
//            robot.rightDrive.setPower(Math.abs(speed));
//
//            // keep looping while we are still active, and there is time left, and both motors are running.
//            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
//            // its target position, the motion will stop.  This is "safer" in the event that the robot will
//            // always end the motion as soon as possible.
//            // However, if you require that BOTH motors have finished their moves before the robot continues
//            // onto the next step, use (isBusy() || isBusy()) in the loop test.
//            while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {
//
//                // Display it for the driver.
//                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
//                telemetry.addData("Path2",  "Running at %7d :%7d",
//                        robot.leftDrive.getCurrentPosition(),
//                        robot.rightDrive.getCurrentPosition());
//                telemetry.update();
//            }
//
//        VuforiaPosistion(0,0,0,0,0,0,0);
//        }
//
