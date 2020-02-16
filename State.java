package org.firstinspires.ftc.teamcode;



public class State {
    public int currentInstruction = 0;
    public RobotState currentState = RobotState.IDLE;

    public State(){
        this.currentInstruction = 0;
        this.currentState = RobotState.IDLE;
    }

    public State(int currentInstruction, RobotState robotState){
        this.currentInstruction = currentInstruction;
        this.currentState = robotState;
    }

    public RobotState getCurrentState() {
        return currentState;
    }

    public int getAutoInstruction() {
        return currentInstruction;
    }

    public void setCurrentState(RobotState currentState) {
        this.currentState = currentState;
    }

    public void setCurrentInstruction(int currentInstruction) {
        this.currentInstruction = currentInstruction;
    }
}

 enum RobotState {
    DRIVING, TURNING, STRAFING, IDLE;
}
