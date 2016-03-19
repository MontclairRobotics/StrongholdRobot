package org.usfirst.frc.team555.robot;

enum autoState1{start, lift, drive, shoot}
enum autoState2{start, lift, move, shoot}

public abstract class StateMachine<enumType> {

	protected enumType currentState;
	protected enumType nextState;
	//public Robot robot;
	public int loopsInState;
	
	public StateMachine(enumType initialState){
		currentState = initialState;
	}
	public abstract enumType calculateNextState();/*{
		return currentState;
	}*/
	
	public void update(){
		Robot.dashboard.putString("state", currentState.toString());
		boolean changed = true;
		while(changed){
			loopsInState++;
			Robot.dashboard.putNumber("loopsInState", (double) loopsInState);
			nextState = calculateNextState();
			changed = !nextState.equals(currentState);
			if(changed){
				loopsInState = 0;
				executeTransition(nextState);
			}
			currentState = nextState;
			executeCurrentState();
		}
	}
	public abstract void executeCurrentState();
	
	public abstract void executeTransition(enumType next);
}
