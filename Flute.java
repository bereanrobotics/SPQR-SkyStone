package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name="Flute", group="blueTest")
public class Flute extends SPQROpMode {
    public void runFlute (){
        int instruction = 0;
        //Initial movement forward
        this.drive(7000, 1, instruction++);

        this.drive(1000, 0.5, instruction++);
        //grab the block using the tow, wait for the tow to come down before
        this.dropTow(instruction++);
        this.sleep(1000, instruction++);

        //reverse away from the blocks
        this.drive(-3750, -1, instruction++);

        //turn right towards the top of the field
        this.turn(-90, 1.0, instruction++);

        //drive across the line
        this.drive(15000, 1.0, instruction++);

        //releasing the block, with a slight pause to make sure it is clear of the tow
        this.raiseTow(instruction++);
        this.sleep(1000, instruction++);

        this.drive(-5000, -1, instruction++);
    }

    public void init(){
        this.hardwareInit();

    }

    public void init_loop () {
        telemetry.addData("Autonomous OpMode 'Flute' has been initialized for: ", getRuntime());
    }

    public void loop(){
        if (time < 30){
            runFlute();
        }

        this.updateTelemetry();
    }
}
