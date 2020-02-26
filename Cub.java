package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name="Cub", group="redTest")
public class Cub extends SPQROpMode {
    public void runCub (){
        int instruction = 0;
        this.drive(7000, 1, instruction++);
        //slow the robot down and move right up to the block
        this.drive(900,0.5, instruction++);

        //grab the block using the tow, wait for the tow to come down before
        this.dropTow(instruction++);

        this.sleep(1000, instruction++);

        //reverse away from the blocks
        this.drive(-2500, -1, instruction++);

        //turn right towards the top of the field
        this.turn(-90, 1.0, instruction++);

        //drive across the line
        this.drive(16000, 1.0, instruction++);

        //turn in preparation to release the block
        this.turn(90, 1.0, instruction++);

        //releasing the block, with a slight pause to make sure it is clear of the tow
        this.raiseTow(instruction++);
        this.sleep(100, instruction++);

        //strafe to the right, all the way to the top of the field
        this.strafe(Dir.RIGHT, 11000, 1, instruction++);

        //drive forward up to the foundation
        this.drive(2850, 1, instruction++);

        //grab the foundation and wait for it to settle
        this.robot.dropTow();
        this.sleep(900, instruction++);

        //drive back slowly pulling the foundation into the building site
        this.drive(-7100, -0.5, instruction++);

        //retract the tow
        this.robot.raiseTow();

        //strafe to the left all the` way to the line
        this.strafe(Dir.LEFT, 16750, 1.0, instruction++);

        //drive in until time runs out (usually does during this function)
        this.drive(3000, 1.0, instruction++);
    }

    public void init(){
        this.hardwareInit();
    }

    public void init_loop () {
            telemetry.addLine("Autonomous OpMode 'Cub' has been initialized.");
    }

    public void loop(){
        if (time < 30){
            runCub();
        }
    }
}
