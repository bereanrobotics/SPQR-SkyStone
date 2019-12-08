package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import static java.lang.Math.abs;
/**
 * Custom Linear OpMode class with extra functions
 *
 * @author Owen peterson
 */
public abstract class SPQRLinearOpMode extends LinearOpMode {

    public long ninetyDegreeTime = 1071;

    public double speed = -0.75;
    public double turnSpeed = -1;

    public HardwareSPQR robot = new HardwareSPQR();

    public void hardwareInit(){
        this.robot.init(hardwareMap);
    }

    public long justSpeed (long miliRatio, double speed){
        return ((long) abs(miliRatio/speed));
    }

    public void driveForTime(double speed, long milliseconds){
        this.robot.setPowers(speed);
        this.sleep(milliseconds);
        this.robot.setPowers(0);
    }

    public void turnForTime(Dir direction, double speed, long milliseconds){
        if (direction == Dir.RIGHT) {
            this.robot.tank(speed, -speed);
        } else {
            this.robot.tank(-speed, speed);
        }
        this.sleep(milliseconds);
        this.robot.setPowers(0);
    }
}