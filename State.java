package org.firstinspires.ftc.teamcode;



public class State {
    public int currentInstruction = 0;
    public RobotState currentState = RobotState.IDLE;
    public double sleepTime = 0;

    public State(){
        this.currentInstruction = 0;
        this.currentState = RobotState.IDLE;
        this.sleepTime = 0;
    }

    public State(int currentInstruction, RobotState robotState){
        this.currentInstruction = currentInstruction;
        this.currentState = robotState;
        this.sleepTime = 0;
    }

    public RobotState getCurrentState() {
        return this.currentState;
    }

    public int getCurrentInstruction() {
        return this.currentInstruction;
    }

    public double getSleepTime (){
        return this.sleepTime;
    }

    public void setCurrentState(RobotState currentState) {
        this.currentState = currentState;
    }

    public void setCurrentInstruction(int currentInstruction) {
        this.currentInstruction = currentInstruction;
    }

    public void setSleepTime (double time){
        this.sleepTime = time;
    }

    public void nextInstruction(){
        this.currentInstruction++;
    }
}

 enum RobotState {
    DRIVING, TURNING, STRAFING, IDLE, SLEEPING;
}
