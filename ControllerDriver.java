package org.firstinspires.ftc.teamcode;

public class ControllerDriver {

    //Joysticks (LJ and RJ)
    double joyDeadzone = 5;
    double rJYDeadzone = joyDeadzone;
    double lJYDeadzone = joyDeadzone;
    double rJYActive = (100 - rJYDeadzone);
    double lJYActive = (100 - lJYDeadzone);
    //some thing that gets the value of the specified joystick,hopefully split in x,y perecentage

    public double joystickMoveActual (double jYPosistion, String stickIdentifier) { //theoritically when something triggers this we go through this to determine how much we actually output
        double joyActual = -1;
        if (stickIdentifier == "R") {
            joyActual = (jYPosistion/rJYActive);
        } else if (stickIdentifier == "L"){
            joyActual = (jYPosistion/lJYActive);
        } else {
            joyActual = (jYPosistion/(100-joyDeadzone));
            System.out.print("Error: Expected 'R' or 'L' in method joystickMoveActual.");
        }
        return joyActual;
    }


    //Deadzones are measured in percent from




}
