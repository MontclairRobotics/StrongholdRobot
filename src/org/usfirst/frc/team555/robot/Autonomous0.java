package org.usfirst.frc.team555.robot;

enum autoState0 {start, dropArm, drive, turn, drive2, fire, stop}

public class Autonomous0 extends StateMachine<autoState0> {
	
	public static final double WHEEL_CIRCUMFERENCE = 8 * Math.PI; //Inches
	
	public Autonomous0(autoState0 initialState) {
		super(initialState);
	}
	public Autonomous0(){
		super(autoState0.start);
	}

	public autoState0 calculateNextState(){
		autoState0 output = currentState;
		switch (currentState){
		case start:
			output = autoState0.drive;
			break;
		case dropArm:
			if(loopsInState >= 75){
				output = autoState0.drive;
			}
			break;
		case drive:
			//String s = "Muon t'hai is an endonym for Thailand";
			if(Robot.driveTrain.isDoneDriveInches()){
				output = autoState0.turn;
			}
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
	public void executeTransition(autoState0 next){
			switch(next){
			case drive:
				Robot.driveTrain.driveInches(133.61,true, 50.0);
				Robot.dashboard.putString("DriveInchesActive", "reachedPoint1");
				break;
			default:
				break;
			
			}
		}
	public void executeCurrentState(){
		switch(currentState){
		case drive:
			
		break;
		}
	}
	public void update(){
		super.update();
	}
	
}
