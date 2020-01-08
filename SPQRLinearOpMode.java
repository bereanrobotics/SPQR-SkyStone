package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Custom Linear OpMode class with extra functions.
 *
 * @author Arkin Solomon
 */
public abstract class SPQRLinearOpMode extends LinearOpMode {

    //Variables
    public final double speed = 0.75;
    public final double turnSpeed = 1;
    public final long ninetyDegreeRightTime = 990;
    public final long ninetyDegreeLeftTime = 970;

    //The switch on the robot that dictates what color it is. (red or blue)
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
    private double wheelRadius = 1;
    private double wheelCircumference = wheelRadius * 2 * Math.PI;

    //Intialize hardware
    public HardwareSPQR robot = new HardwareSPQR();
    public void hardwareInit(){
        this.robot.init(hardwareMap);
    }

    //Makes these easier to type
    public void moveArm(int level, double speed){
        this.robot.moveArm(level, speed);
    }
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
        this.robot. backward();
        this.sleep(100);
        this.robot.stopMoving();
    }

    //Calculates distance robot has traveled
    public double calculateDistance(){
      double encoder = this.driveAverage()
      return (encoder / fullCircle) * wheelCircumference
    }

    //Returns an array plus or minus a change given of a value given
    private int[] plusOrMinus(int value, int change){
        return new int[] {value - change, value + change};
    }

    //Get average distance traveled
    private double driveAverage(){
      int[] encoderPositions = {this.robot.leftFrontDrive.getPosition(), this.robot.rightFrontDrive.getPosition(), this.robot.leftBackDrive.getPosition(), this.robot.rightBackDrive.getPosition()};
      int sum = 0;
      for (int encoderPosition : encoderPositions){
        sum += encoderPosition;
      }
      return sum / encoderPositions.length
    }
  }
