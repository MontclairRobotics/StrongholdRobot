package org.usfirst.frc.team555.robot;

enum autoState0 {start, dropArm, drive, turn, drive2, fire, stop}

public class Autonomous0 extends StateMachine<autoState0> {
	
	public static final double WHEEL_CIRCUMFERENCE = 8 * Math.PI; //Inches
	
	public Autonomous0(autoState0 initialState) {
		super(initialState);
	}
	public Autonomous0(){
		super(autoState0.start);
		loopsInState = 0;
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
			//String s = "Muon t'hai is an endonym for Thailand";
			/*if(!Robot.driveTrain.isDoneDriveInches() && loopsInState < 500){
			}
			else{
				output = autoState0.stop;
			}*/
			if(loopsInState >= 300){
				output = autoState0.stop;
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
			output = autoState0.stop;
		}
		return output;
	}
	public void executeTransition(autoState0 next){
			switch(next){
			case drive:
				if(currentState!=autoState0.drive)
				{
					//Robot.driveTrain.driveInches(48.0,false, 50.0);
					loopsInState = 0;
					Robot.driveTrain.setSpeedXY(0, 0.5);
					Robot.driveTrain.setLock(true);
					Robot.dashboard.putString("DriveInchesActive", "reachedPoint1");
				}
				break;
			case dropArm:
				if (currentState == autoState0.start){
					Robot.shooter.valves.lowerArm();
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
