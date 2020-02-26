package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public abstract class SPQROpMode extends OpMode {
    DcMotor.ZeroPowerBehavior previousBehavior;

    public State robotState = new State();
    public int autoInstruction = 0;

    public final int towFoundation = 100;
    public final int towBlock = 100;
    public final int towDefault = 500;

    //Variables
    public final double speed = 0.75;
    public final double ppr = 280;
    public final double degppr = 21.1;
    public final double circleRadius = 245.7768;

    //Values for the wheels
    private final double wheelRadius = 5*25.4;
    private final double wheelCircumference = wheelRadius * 2 * Math.PI;

    public int leftFrontEncoder;
    public int rightFrontEncoder;
    public int leftBackEncoder;
    public int rightBackEncoder;

    public HardwareSPQR robot = new HardwareSPQR();

    /**
     * This method is an abstraction to initialize the hardware of the robot.
     */
    public void hardwareInit(){
        this.robot.init(hardwareMap);
    }

    /**
     * This method is an abstraction to close the block-grabber.
     */
    public void grabBlock(){
        this.robot.grabBlock();
    }

    /**
     * This method is an abstraction to open the block-grabber.
     */
    public void releaseBlock(){
        this.robot.releaseBlock();
    }

    /**
     * This method moves the robot forward until the robot's color sensor is above the tape. It
     * assumes that the robot is already facing the direction of the tape and has no obstacles
     * between it and the tape. If a tolerated color to the right color is not detected, the robot
     * will not stop moving.
     *
     * @param tapeColor An array of the color of the tape of which the robot is to stop over. Given
     *                  in the format {RED, GREEN, BLUE} as detected by a sensor of the same type as
     *                  the one on the underside of the robot.
     * @param change The tolerance of error of each component of the color that is detected by the
     *               color sensor.
     */
    public void stopAtTape(int[] tapeColor, int change, int instruction){
        this.robot.lineParkSensor.enableLed(true);
        this.robot.setPowers(.6);
        boolean isOnLine = false;
        while (!isOnLine) {
            int[] r = this.plusOrMinus(this.robot.lineParkSensor.red(), change);
            int[] g = this.plusOrMinus(this.robot.lineParkSensor.green(), change);
            int[] b = this.plusOrMinus(this.robot.lineParkSensor.blue(), change);

            //Check if the robot is over the line
            isOnLine = (((tapeColor[0] > r[0]) && (tapeColor[0] < r[1])) && ((tapeColor[1] > g[0]) && (tapeColor[1] < g[1])) && ((tapeColor[2] > b[0]) && (tapeColor[2] < b[1])));
        }
        this.robot.lineParkSensor.enableLed(false);
        this.robot.stopMoving();
    }

    /**
     * This method calculates the approximate distance that the robot has traveled with the average
     * encoder value of all of the robot's drive motor's encoders.
     *
     * @return The approximate distance in centimeters (or millimeters, unsure) that the robot has
     * traveled since the last time the encoders were reset to the zero position.
     */
    public double calculateDistance(){
        double encoder = this.driveAverage();
        return (encoder / ppr) * wheelCircumference;
    }

    /**
     * This method calculates and returns the average encoder value of all of the robot's drive
     * motors using the absolute values of each encoder position of each drive motor of the robot.
     *
     * @return A double greater than zero which represents the average encoder values of all of the
     * robot's drive motors.
     */
    public double getAverageEncoder(){
        int[] encoderPositions = {this.robot.leftFrontDrive.getCurrentPosition(), this.robot.rightFrontDrive.getCurrentPosition(), this.robot.leftBackDrive.getCurrentPosition(), this.robot.rightBackDrive.getCurrentPosition()};
        int sum = 0;
        for (int encoderPosition : encoderPositions){
            sum += Math.abs(encoderPosition);
        }
        return sum / encoderPositions.length;
    }

    /**
     * This method takes a value and determines the two values equidistant from the given value with
     * both having a distance of a given value.
     *
     * @param value An integer to be used to calculate final points based on change.
     * @param change An integer which is the absolute distance from the value.
     * @return An array of integers with two indexes with index zero being the smallest possible
     * integer from the given value with a given distance and with index one being the largest
     * possible integer from the given value with a given distance.
     */
    private int[] plusOrMinus(int value, int change){
        change = Math.abs(change);
        return new int[] {value - change, value + change};
    }

    /**
     * This method synchronously turns the robot to a specified angle (in degrees) that is relative
     * to the robot at a given speed. This uses calculations based off of the definition of radians.
     *
     * @param angle A double which is the relative angle (in degrees) to turn.
     * @param speed A double between -1.0 and 1.0 which is the speed at which the robot is to turn.
     *              This value will be assigned as the speed of the motors
     */
    public void turn2 (double angle, double speed, int instruction) {
        this.previousBehavior = this.robot.leftFrontDrive.getZeroPowerBehavior();
        this.robot.setDriveZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        resetEncoders(DcMotor.RunMode.RUN_TO_POSITION);
        int encoderTarget = (int) (((circleRadius * (Math.toRadians(angle))) * wheelCircumference * 2) / ppr);
        if (angle > 0) {
            this.robot.leftFrontDrive.setTargetPosition(encoderTarget);
            this.robot.leftBackDrive.setTargetPosition(encoderTarget);
            this.robot.rightFrontDrive.setTargetPosition(-encoderTarget);
            this.robot.rightBackDrive.setTargetPosition(-encoderTarget);
            this.robot.tank(-speed, speed);
        } else if (angle < 0) {
            this.robot.leftFrontDrive.setTargetPosition(-encoderTarget);
            this.robot.leftBackDrive.setTargetPosition(-encoderTarget);
            this.robot.rightFrontDrive.setTargetPosition(encoderTarget);
            this.robot.rightBackDrive.setTargetPosition(encoderTarget);
            this.robot.tank(speed, -speed);
        }
        while (drivesBusy()){
            updateTelemetry();
        }
        this.robot.setDriveZeroPowerBehavior(previousBehavior);
    }

    /**
     * This method synchronously turns the robot to a specified angle (in degrees) that is relative
     * to the robot at a given speed. This uses a test based encoder with degrees per pulse per
     * revolution and is relevant to the current SPQR robot only.
     *
     *
     * @param angle A double which is the relative angle (in degrees) to turn.
     * @param speed A double between -1.0 and 1.0 which is the speed at which the robot is to turn.
     *              This value will be assigned as the speed of the motors
     */
    public void turn (double angle, double speed, int instruction){

        if (instruction == robotState.getCurrentInstruction()){
            if (robotState.currentState == RobotState.IDLE){
                this.robotState.setCurrentState(RobotState.TURNING);
                previousBehavior = this.robot.leftFrontDrive.getZeroPowerBehavior();
                this.robot.setDriveZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                int encoderTarget = (int) (Math.abs(this.degppr*angle));
                if (angle > 0) {
                    this.robot.leftFrontDrive.setTargetPosition(encoderTarget);
                    this.robot.leftBackDrive.setTargetPosition(encoderTarget);
                    this.robot.rightFrontDrive.setTargetPosition(-encoderTarget);
                    this.robot.rightBackDrive.setTargetPosition(-encoderTarget);
                    resetEncoders(DcMotor.RunMode.RUN_TO_POSITION);
                    this.robot.tank(-speed, speed);
                } else if (angle < 0) {
                    this.robot.leftFrontDrive.setTargetPosition(-encoderTarget);
                    this.robot.leftBackDrive.setTargetPosition(-encoderTarget);
                    this.robot.rightFrontDrive.setTargetPosition(encoderTarget);
                    this.robot.rightBackDrive.setTargetPosition(encoderTarget);
                    resetEncoders(DcMotor.RunMode.RUN_TO_POSITION);
                    this.robot.tank(speed, -speed);
                }
            }
            if (!drivesBusy()){
                this.robotState.nextInstruction();
                this.robot.setDriveZeroPowerBehavior(previousBehavior);
                this.robotState.setCurrentState(RobotState.IDLE);
            }
        }
    }

    /**
     * This method drives the robot forward by a given distance in centimeters (or millimeters,
     * unsure) at a specified speed. The robot will go backwards if the speed given is a value less
     * than zero.
     *
     * @param distance A double which represents the distance for the robot to travel in centimeters
     *                 (or millimeters, unsure)
     * @param speed A double between -1.0 and 1.0 which is the speed at which the robot is to drive.
     */

    public void strafe (Dir direction, double distance, double speed, int instruction){
        if (instruction == robotState.getCurrentInstruction()) {
            if (robotState.currentState == RobotState.IDLE) {
                this.robotState.setCurrentState(RobotState.STRAFING);
                previousBehavior = this.robot.leftFrontDrive.getZeroPowerBehavior();
                this.robot.setDriveZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                int encoderTarget = (int) ((distance / wheelCircumference) * ppr);
                this.robot.leftFrontDrive.setTargetPosition((direction == Dir.LEFT) ? encoderTarget : -encoderTarget);
                this.robot.leftBackDrive.setTargetPosition((direction == Dir.LEFT) ? -encoderTarget : encoderTarget);
                this.robot.rightFrontDrive.setTargetPosition((direction == Dir.LEFT) ? -encoderTarget : encoderTarget);
                this.robot.rightBackDrive.setTargetPosition((direction == Dir.LEFT) ? encoderTarget : -encoderTarget);
                resetEncoders(DcMotor.RunMode.RUN_TO_POSITION);
                this.robot.strafe(direction, speed);
            }
        }
        if (!drivesBusy()){
            this.robotState.nextInstruction();
            this.robot.setDriveZeroPowerBehavior(previousBehavior);
            this.robotState.setCurrentState(RobotState.IDLE);
        }
    }
    public void drive(double distance, double speed, int instruction){
        if (instruction == robotState.getCurrentInstruction()) {
            if (robotState.currentState == RobotState.IDLE) {
                this.robotState.setCurrentState(RobotState.DRIVING);
                this.previousBehavior = this.robot.leftFrontDrive.getZeroPowerBehavior();
                this.robot.setDriveZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                int encoderTarget = (int) ((distance/wheelCircumference)*ppr);
                this.robot.setDriveTargetPosition(-encoderTarget);
                resetEncoders(DcMotor.RunMode.RUN_TO_POSITION);
                this.robot.setPowers(speed);
            }
            if (!drivesBusy()){
                this.robotState.nextInstruction();
                this.robot.setDriveZeroPowerBehavior(previousBehavior);
                this.robotState.setCurrentState(RobotState.IDLE);
            }
        }
    }

    /**
     * This method resets the encoder positions of the drive motors to zero and adds the current
     * encoder position to the total encoder positions.
     */
    public void resetEncoders(DcMotor.RunMode runMode){
        this.leftFrontEncoder += this.robot.leftFrontDrive.getCurrentPosition();
        this.rightFrontEncoder += this.robot.rightFrontDrive.getCurrentPosition();
        this.leftBackEncoder += this.robot.leftBackDrive.getCurrentPosition();
        this.rightBackEncoder += this.robot.rightBackDrive.getCurrentPosition();

        this.robot.setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.robot.setDriveMode(runMode);
    }

    /**
     * This method calculates and returns the average encoder value of all of the robot's drive
     * motors using the values of each encoder position of each drive motor of the robot.
     *
     * @return A double which represents the average encoder values of all of the robot's drive motors.
     */
    public double driveAverage(){
        int[] encoderPositions = {this.robot.leftFrontDrive.getCurrentPosition(), this.robot.rightFrontDrive.getCurrentPosition(), this.robot.leftBackDrive.getCurrentPosition(), this.robot.rightBackDrive.getCurrentPosition()};
        int sum = 0;
        for (int encoderPosition : encoderPositions){
            sum += encoderPosition;
        }
        return sum / encoderPositions.length;
    }

    /**
     * This method updates the telemetry data on both the driver station and the robot controller
     * with common debugging information.
     */
    public void updateTelemetry(){
        telemetry.addData("Current instruction #: ", this.robotState.getCurrentInstruction());
        telemetry.addData("The robot is currently: ", this.robotState.getCurrentState());
        telemetry.addData("The opmode has been running for: ", getRuntime());
        telemetry.addLine();

        telemetry.addData("Distance", calculateDistance());

        telemetry.addData("Left Front Velocity", ((DcMotorEx) this.robot.leftFrontDrive).getVelocity());
        telemetry.addData("Right Front Velocity", ((DcMotorEx) this.robot.rightFrontDrive).getVelocity());
        telemetry.addData("Left Back Velocity", ((DcMotorEx) this.robot.leftBackDrive).getVelocity());
        telemetry.addData("Right Back Velocity", ((DcMotorEx) this.robot.leftFrontDrive).getVelocity());

        telemetry.addData("Left Front Target", this.robot.leftFrontDrive.getTargetPosition());
        telemetry.addData("Right Front Target", this.robot.rightFrontDrive.getTargetPosition());
        telemetry.addData("Left Back Target", this.robot.leftBackDrive.getTargetPosition());
        telemetry.addData("Right Back Target", this.robot.rightBackDrive.getTargetPosition());

        telemetry.addData("left Front TempEncoder", this.robot.leftFrontDrive.getCurrentPosition());
        telemetry.addData("right Front TempEncoder", this.robot.rightFrontDrive.getCurrentPosition());
        telemetry.addData("left Back TempEncoder", this.robot.leftBackDrive.getCurrentPosition());
        telemetry.addData("right Back TempEncoder", this.robot.rightBackDrive.getCurrentPosition());
        telemetry.addData("left Front Encoder", this.leftFrontEncoder);
        telemetry.addData("right Front Encoder", this.rightFrontEncoder);
        telemetry.addData("left Back Encoder", this.leftBackEncoder);
        telemetry.addData("right Back Encoder", this.rightBackEncoder);
        telemetry.update();
    }

    public void dropTow(int instruction){
        if (instruction == this.robotState.getCurrentInstruction()){
            this.robot.dropTow();
            this.robotState.nextInstruction();
        }
    }

    public void raiseTow (int instruction){
        if (instruction == this.robotState.getCurrentInstruction()){
            this.robot.raiseTow();
        }
    }

    public void sleep (int time, int instruction){
        if (instruction == this.robotState.getCurrentInstruction()){
            if(this.robotState.getCurrentState() == RobotState.IDLE){
                this.robotState.setCurrentState(RobotState.SLEEPING);
                this.robotState.setSleepTime(getRuntime());

            }
            if (robotState.getSleepTime()+time<getRuntime()){
                this.robotState.nextInstruction();
            }
        }
    }

    /**
     *  This method is an abstraction that returns true if at least one motor is running, false if
     *  no motors are running.
     * @return returns the boolean true if 1 or more drives are running, false otherwise.
     */
    public boolean drivesBusy() {
        return (this.robot.leftFrontDrive.isBusy() || this.robot.rightFrontDrive.isBusy() || this.robot.leftBackDrive.isBusy() || this.robot.rightBackDrive.isBusy());
    }
}
