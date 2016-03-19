package org.usfirst.frc.team555.robot;

enum obsStates{start, ingress, egress, stop}

public class ObstacleStateMachine extends StateMachine<obsStates>{
	
	obstacles obstacle;
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
		switch (obstacle){
		case terrain:
			
		}
		return false;
	}
	protected boolean egressDone(){
		return false;
	}
	
	public boolean isComplete(){
		return (currentState == obsStates.stop);	
	}

	@Override
	public void executeTransition(obsStates next) {
		switch (next){
		
		}
	}
	
	

}
