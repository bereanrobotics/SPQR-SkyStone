package org.firstinspires.ftc.teamcode;


public class RedAutoBlockTime extends SPQRLinearOpMode {

    private double robotSpeed = 0.75;
    private int[] tapeColor = {2600, 1660, 600};
    private Dir directionOne = Dir.RIGHT;
    private Dir directionTwo = Dir.LEFT;
    @Override
    public void runOpMode(){
        this.hardwareInit();

        waitForStart();

        this.robot.moveArm(1, 1);
        this.driveForTime(this.robotSpeed, 1000);
        this.robot.grabBlock();
        this.robot.moveArm(2, 1);
        this.turnForTime90(directionOne, turnSpeed);
        this.stopAtTape(this.tapeColor, 500);
        this.robot.releaseBlock();
    }
}
