package org.usfirst.frc.team555.robot;

enum states {start, obstacle, turn, drive2, fire, stop}
enum obstacles {terrain, moat, ramps, chev, gate, wall, lowBar, none}

public class AutonomousAdjustable extends StateMachine<states> {
	
	private boolean direction;
	private int location;
	private obstacles obstacle;
	private ObstacleStateMachine driver;
	
	public static final double WHEEL_CIRCUMFERENCE = 8 * Math.PI;
	
	public AutonomousAdjustable() {
		this(true, 0 ,obstacles.none);
	}
	
	public AutonomousAdjustable(boolean dir,int loc, obstacles obs)
	{
		super(states.start);
		direction=dir;
		location=loc;
		obstacle=obs;
		driver=new ObstacleStateMachine(obs);
		
	}

	public states calculateNextState(){
		states output = currentState;
		switch (currentState){
		case start:
			output = states.obstacle;
			break;
		case obstacle:
			if(true/*driver.isDone()*/){
				output = states.turn;
			}
			break;
		case turn:
			break;
		case drive2:
			break;
		case fire:
			break;
		case stop:
			output = states.stop;
		}
		return output;
	}
	
	public void executeTransition(states next){
			switch(next){
			case obstacle:
				break;
			default:
				break;
			
			}
		}
	
	public void executeCurrentState(){
		switch(currentState){
		default:
			break;
		}
	}	
}
