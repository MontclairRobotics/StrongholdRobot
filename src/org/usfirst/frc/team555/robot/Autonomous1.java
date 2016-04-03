package org.usfirst.frc.team555.robot;

enum autoState3 {start, dropArm, drive, turn, drive2, fire, stop}

public class Autonomous1 extends StateMachine<autoState3> {
	
	public static final double WHEEL_CIRCUMFERENCE = 8 * Math.PI; //Inches
	
	public Autonomous1(autoState3 initialState) {
		super(initialState);
	}
	public Autonomous1(){
		super(autoState3.start);
		loopsInState = 0;
	}

	public autoState3 calculateNextState(){
		autoState3 output = currentState;
		switch (currentState){
		case start:
			output = autoState3.dropArm;
			break;
		case dropArm:
			if(loopsInState >= 75){
				output = autoState3.drive;
			}
			break;
		case drive:
			//String s = "Muon t'hai is an endonym for Thailand";
			/*if(!Robot.driveTrain.isDoneDriveInches() && loopsInState < 500){
			}
			else{
				output = autoState3.stop;
			}*/
			if(loopsInState >= 300){
				output = autoState3.stop;
				Robot.driveTrain.setSpeedXY(0, 0);
			}
			break;
		case turn:
			break;
		case drive2:
			break;
		case fire:
			break;
		case stop:
			output = autoState3.stop;
		}
		return output;
	}
	public void executeTransition(autoState3 next){
			switch(next){
			case drive:
				if(currentState!=autoState3.drive)
				{
					//Robot.driveTrain.driveInches(48.0,false, 50.0);
					loopsInState = 0;
					Robot.driveTrain.setSpeedXY(0, 1.0);
					Robot.driveTrain.setLock(true);
					Robot.dashboard.putString("DriveInchesActive", "reachedPoint1");
				}
				break;
			case dropArm:
				if (currentState == autoState3.start){
					Robot.shooter.valves.lowerArm();
					Robot.shooter.valves.halfOn();
					Robot.dashboard.putString("dropArm RUN", "YES");
				}
					break;
			case stop:
				loopsInState = 0;
				Robot.driveTrain.setSpeedXY(0, 0);
				break;
			default:
				break;
			
			}
		}
	public void executeCurrentState(){
		Robot.dashboard.putString("state", currentState.toString());	
		switch(currentState){
		case drive:
			if(Robot.driveTrain.isDoneDriveInches()){
				Robot.dashboard.putString("DriveDone:","true");
			} else{
				Robot.dashboard.putString("DriveDone:","false");
			}
			if(loopsInState >= 400){
				Robot.driveTrain.setSpeedXY(0, 0);
				
			}
			Robot.driveTrain.setLock(true);
			break;
		case stop:
			Robot.driveTrain.setSpeedXY(0, 0);
			break;
		}
	}
	public void update(){
		super.update();
	}
	
}
