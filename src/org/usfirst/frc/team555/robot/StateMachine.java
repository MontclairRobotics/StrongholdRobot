package org.usfirst.frc.team555.robot;

enum autoState1{start, lift, drive, shoot}
enum autoState2{start, lift, move, shoot}

public class StateMachine<enumType> {

	protected enumType currentState;
	protected enumType nextState;
	public int loopsInState;
	
	public StateMachine(Robot robot, enumType initialState){
		
	}
	public enumType calculateNextState(){
		return currentState;
	}
	
	public void update(){
		boolean changed = true;
		while(changed){
			loopsInState++;
			nextState = calculateNextState();
			changed = !nextState.equals(currentState);
			if(changed){
				loopsInState = 0;
			}
			executeTransition(currentState, nextState);
			currentState = nextState;
			executeCurrentState();
		}
	}
	public void executeCurrentState(){
		
	}
	
	public void executeTransition(enumType current, enumType next){
		
	}
}
