package org.usfirst.frc.team555.robot;

enum autoState0 {start, dropArm, drive, turn, drive2, fire, stop}

public class Autonomous0 extends StateMachine<autoState0> {
	
	public static final double WHEEL_CIRCUMFERENCE = 8 * Math.PI;
	
	public Autonomous0(Robot robot, autoState0 initialState) {
		super(robot, initialState);
	}

	public autoState0 calculateNextState(){
		autoState0 output = currentState;
		switch (currentState){
		case start:
			output = autoState0.dropArm;
			break;
		case dropArm:
			if(loopsInState >= 75){
				output = autoState0.drive;
			}
			break;
		case drive:
			
			break;
		case turn:
			break;
		case drive2:
			break;
		case fire:
			break;
		case stop:
			output = autoState0.stop;
		}
		return output;
	}
	
}
