package org.usfirst.frc.team555.robot;

enum obsStates{start, ingress, egress, stop}

public class ObstacleStateMachine extends StateMachine<obsStates>{
	
	obstacles obstacle;
	private boolean driveBegun = false;
	
	public ObstacleStateMachine(obsStates initialState) {
		super(initialState);
	}
	
	public ObstacleStateMachine(obstacles obs){
		super(obsStates.start);
		obstacle = obs;
	}

	public obsStates calculateNextState() {
		obsStates output = currentState;
		switch (currentState){
		case start:
			output = obsStates.ingress;
			break;
		case ingress:
			if (ingressDone()){
				output = obsStates.egress;
			}
			break;
		case egress:
			if(egressDone()){
				output = obsStates.stop;
			}
			break;
		}
		return output;
	}

	public void executeCurrentState() {
		
	}
	
	protected boolean ingressDone(){
		boolean output = true;
		if (driveBegun && !Robot.driveTrain.isDoneDriveInches()){
			output = false;
		}
		else{
			driveBegun = false;
		}
		return output;
	}
	protected boolean egressDone(){
		boolean output = true;
		if (driveBegun && !Robot.driveTrain.isDoneDriveInches()){
			output = false;
		}
		else{
			driveBegun = false;
		}
		return output;
	}
	
	public boolean isDone(){
		return (currentState == obsStates.stop);	
	}

	@Override
	public void executeTransition(obsStates next) {
		switch (next){
		case ingress:
			switch (obstacle){
			case terrain:
				Robot.driveTrain.driveInches(48, 0.5);
				driveBegun = true;
				break;
			case moat:
				Robot.driveTrain.driveInches(24, 0.99);
				driveBegun = true;
				break;
			case ramps:
				Robot.driveTrain.driveInches(48, 0.5);
				driveBegun = true;
				break;
			case chev:
				break;
			case gate:
				break;
			case lowBar:
				Robot.driveTrain.driveInches(48, 0.3);
				driveBegun = true;
				break;
			case wall:
				break;
			case none:
				Robot.driveTrain.driveInches(48, 1.0);
				driveBegun = true;
				break;
			}
			break;
		case egress:
			switch (obstacle){
			case terrain:
				break;
			case moat:
				Robot.driveTrain.driveInches(24, 0.5);
				driveBegun = true;
				break;
			case ramps:
				break;
			case chev:
				break;
			case gate:
				break;
			case lowBar:
				break;
			case wall:
				break;
			case none:
				break;
			}
			break;
			
		}
	}
	
	

}
