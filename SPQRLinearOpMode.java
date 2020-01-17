package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Custom Linear OpMode class with extra functions.
 *
 * @author Arkin Solomon
 * @author Owen Peterson
 */
public abstract class SPQRLinearOpMode extends LinearOpMode {

    //Variables
    public final double speed = 0.75;
    public final double turnSpeed = 1;
    public final long ninetyDegreeRightTime = 990;
    public final long ninetyDegreeLeftTime = 970;

    //The switch on the robot that dictates what color it is. (red or blue)

    public final double ppr = 280;
    public final double errorScalar = 5;

    private boolean teamSwitch;
    private Color teamColor;

    //The switch on the robot that determines the starting posistion (front or back)
    private boolean positionSwitch;
    private Position position;

    //The switch on the robot that determines which lane it will primarily travel on (inside, outside)
    private boolean laneSwitch;
    private Lane lane;

    //The switch on the robot that determines whether to try and pull the foundation into the area
    private boolean foundationSwitch;

    //The switch on the robot that determines whether to try and pickup a block
    private boolean blockSwitch;

    //Values for the wheels
    private final double wheelRadius = 5*25.4;
    private final double wheelCircumference = wheelRadius * 2 * Math.PI;

    private int leftFrontEncoder;
    private int rightFrontEncoder;
    private int leftBackEncoder;
    private int rightBackEncoder;


    //Intialize hardware
    public HardwareSPQR robot = new HardwareSPQR();
    public void hardwareInit(){
        this.robot.init(hardwareMap);
    }

    //Makes these easier to type
    public void grabBlock(){
        this.robot.grabBlock();
    }
    public void releaseBlock(){
        this.robot.releaseBlock();
    }

    //Stops the robot over the tape
    public void stopAtTape(int[] tapeColor, int change){
        this.robot.setPowers(.6);
        boolean isOnLine = false;
        while (!isOnLine && this.opModeIsActive()) {
            int[] r = this.plusOrMinus(this.robot.lineParkSensor.red(), change);
            int[] g = this.plusOrMinus(this.robot.lineParkSensor.green(), change);
            int[] b = this.plusOrMinus(this.robot.lineParkSensor.blue(), change);
            isOnLine = (((tapeColor[0] > r[0]) && (tapeColor[0] < r[1])) && ((tapeColor[1] > g[0]) && (tapeColor[1] < g[1])) && ((tapeColor[2] > b[0]) && (tapeColor[2] < b[1])));
        }
        this.robot.lineParkSensor.enableLed(false);
        this.robot.backward();
        this.sleep(100);
        this.robot.stopMoving();
    }

    //Calculates distance robot has traveled
    public double calculateDistance(){
      double encoder = this.driveAverage();
      return (encoder / 280) * wheelCircumference;
    }

    public double getDistance(int encoder){
        return (encoder / 280) * wheelCircumference;
    }

    public double calculateDistance(double start){
        return calculateDistance() - start;
    }

    //Returns an array plus or minus a change given of a value given
    public int[] plusOrMinus(int value, int change){
        return new int[] {value - change, value + change};
    }

    public void turn (double angle, double speed){
        resetEncoders();
if (angle > 0) {
this.robot.leftFrontDrive.setPower(speed);
this.robot.leftBackDrive.setpower(speed);
this.robot.rightFrontDrive.setPower(-speed);
this.robot.rightBackDrive.setPower(-speed);
} else if (angle < 0) {

this.robot.leftFrontDrive.setPower(-speed);
this.robot.leftBackDrive.setpower(-speed);
this.robot.rightFrontDrive.setPower(speed);
this.robot.rightBackDrive.setPower(speed);

} else {
break;
}

while (false){ //insert condition that is true while robot has not reached target

checkRate(Orientation.VERTICAL)

}

this.robot.stopMoving();
    }

    public void drive (double distance, double speed){
        resetEncoders();
        double tempDistanceStart = calculateDistance();

this.robot.setPowers(speed);

        while(calculateDistance(tempDistanceStart) < distance){
        
            checkRate(Orientation.HORIZONTAL);
        }
        this.robot.stopMoving();
    }

    public void checkRate (Orientation wheelsCompare){
        double frontLeft = this.robot.leftFrontDrive.getCurrentPosition();
        double frontRight = this.robot.rightFrontDrive.getCurrentPosition();
        double backLeft = this.robot.leftBackDrive.getCurrentPosition();
        double backRight = this.robot.rightBackDrive.getCurrentPosition();
        double error;
        double power;
        if (wheelsCompare == Orientation.HORIZONTAL){
            if (frontLeft > frontRight){
                error = frontLeft - frontRight;
                power = this.speed-(error/this.errorScalar);
                this.robot.leftFrontDrive.setPower(power);
            } else if (frontRight > frontLeft){
                error = frontRight - frontLeft;
                power = this.speed-(error/this.errorScalar);
                this.robot.rightFrontDrive.setPower(power);
            }
            if (backLeft > backRight){
                error = backLeft - backRight;
                power = this.speed-(error/this.errorScalar);
                this.robot.leftBackDrive.setPower(power);
            } else if (backRight > backLeft){
                error = backRight - backLeft;
                power = this.speed-(error/this.errorScalar);
                this.robot.rightBackDrive.setPower(power);
            }
        } else if (wheelsCompare == Orientation.VERTICAL){
            if (frontLeft > backLeft){
                error = frontLeft - backLeft;
                power = this.speed-(error/this.errorScalar);
                this.robot.leftFrontDrive.setPower(power);
            } else if (backLeft > frontLeft){
                error = backLeft - frontLeft;
                power = this.speed-(error/this.errorScalar);
                this.robot.leftBackDrive.setPower(power);
            }
            if (frontRight > backRight){
                error = frontRight - backRight;
                power = this.speed-(error/this.errorScalar);
                this.robot.leftFrontDrive.setPower(power);
                } else if (backRight > frontRight){
                error = backRight - frontRight;
                power = this.speed-(error/this.errorScalar);
                this.robot.rightBackDrive.setPower(power);
            }
        } else if (wheelsCompare == Orientation.DIAGONAL){
            if (frontLeft > backRight){
                error = frontLeft - backRight;
                power = this.speed-(error/this.errorScalar);
                this.robot.leftFrontDrive.setPower(power);
            } else if (backRight > frontLeft){
                error = backRight - frontLeft;
                power = this.speed-(error/this.errorScalar);
                this.robot.rightBackDrive.setPower(power);
            }
            if (frontRight > backLeft){
                error = frontRight - backLeft;
                power = this.speed-(error/this.errorScalar);
                this.robot.rightFrontDrive.setPower(power);
            } else if (backLeft > frontRight){
                error = backLeft - frontRight;
                power = this.speed-(error/this.errorScalar);
                this.robot.rightBackDrive.setPower(power);
            }
        }
    }
    public void resetEncoders(){
        this.leftFrontEncoder += this.robot.leftFrontDrive.getCurrentPosition();
        this.rightFrontEncoder += this.robot.rightFrontDrive.getCurrentPosition();
        this.leftBackEncoder += this.robot.leftBackDrive.getCurrentPosition();
        this.rightBackEncoder += this.robot.rightBackDrive.getCurrentPosition();

        this.robot.leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.robot.rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.robot.leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.robot.rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    //Get average distance traveled
    public double driveAverage(){
      int[] encoderPositions = {this.robot.leftFrontDrive.getCurrentPosition(), this.robot.rightFrontDrive.getCurrentPosition(), this.robot.leftBackDrive.getCurrentPosition(), this.robot.rightBackDrive.getCurrentPosition()};
      int sum = 0;
      for (int encoderPosition : encoderPositions){
        sum += encoderPosition;
      }
      return sum / encoderPositions.length;
    }
  }
