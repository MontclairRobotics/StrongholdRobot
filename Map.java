package org.usfirst.frc.team555.robot;

public class Map {
	
	
	//TALON CONFIG
	/*public static final int FRONT_LEFT = 0;
	public static final int REAR_LEFT = 1;
	public static final int FRONT_RIGHT = 2;
	public static final int REAR_RIGHT = 3;
	*/
	//VICTOR CONFIG
	public static final int FRONT_LEFT = 0;
	public static final int REAR_LEFT = 1;
	public static final int FRONT_RIGHT = 2;
	public static final int REAR_RIGHT = 3;
	
	public static final int SHOOTER_PNEUMATICS=2;
	
    public static final int[][] MOTOR_PORTS={
        //Motor port, encoder port 1, encoder port 2
    	//TODO: Actual values
        {1,0,1},
        {2,0,1},
        {3,2,3},
        {4,2,3}
    };
    
    public static final int[][] SHOOTER_MOTORS = {
    		//Wheel 1, wheel 2, pneumatic valve 1, valve 2
    		{6,7, 1},
    		{7,2,3}
    };
    
    public static final int[] LIFT_SOLENOID_PORTS={
    		/*0,1,2*/
    		0,1
    };
    
    public static final int[] SHOOTER_SOLENOID_PORTS={
    		3
    };
    
    //BEGIN USELESS STUFF
    
    public static int getMotorPort(int id) {
    	return MOTOR_PORTS[id][0];
    }
    
    public static int getEncoderPort1(int id) {
    	return MOTOR_PORTS[id][1];
    }
    
    public static int getEncoderPort2(int id) {
    	return MOTOR_PORTS[id][2];
    }
    
    
    
    public static int getShooterWheel1(int id) {
    	return SHOOTER_MOTORS[id][0];
    }
    
    public static int getShooterWheel2(int id) {
    	return SHOOTER_MOTORS[id][1];
    }
    
    public static int getValve1(int id) {
    	return SHOOTER_MOTORS[id][2];
    }
    
    public static int getValve2(int id) {
    	return SHOOTER_MOTORS[id][3];
    }
    
}
