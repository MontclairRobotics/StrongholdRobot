package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Shooter
{
    private ShooterMotor[] wheels;
    private Solenoid[] valves;
    
    private char mode;
    private double shootSpd;
    private int timeToStop;
    
    public static final int REEL_TIME=10,SHOOT_TIME=5,ACTIVATE_SHOOTER_TIME=1;
    
    public static final double REEL_SPEED=-0.5;
    
    public Shooter()
    {
    	wheels= new ShooterMotor[2];
        
    	for(int i=0;i<Map.SHOOTER_MOTORS.length;i++)
    	{
    		wheels[i] = new ShooterMotor(i);
    	}
        wheels[1].setInverted(true);

        valves=new Solenoid[2];
        for(int i=0;i<Map.SOLINOID_PORTS.length;i++)
        {
        	valves[i] = new Solenoid(Map.SOLINOID_PORTS[i]);
        }
        mode='0';
        timeToStop=0;
    }
    
    public void reelIn() {
    	mode='r';
    	timeToStop=REEL_TIME;
    }
    
    public void shoot(double speed) {
    	mode='s';
    	shootSpd=speed;
    	timeToStop=SHOOT_TIME;
    }
    
    public void stop()
    {
    	mode='0';
    }
    
    public void activateShooter()
    {
    	//TODO: PUT STUFF HERE
    }
    
    public void update()
    {
    	if(mode=='s' && timeToStop<ACTIVATE_SHOOTER_TIME)
    	{
    		activateShooter();
    	}
    	if(timeToStop<=0)
    	{
    		mode='0';
    	}
    	timeToStop--;
    	
    	for(ShooterMotor motor : wheels) {
    		switch(mode)
    		{
    		case 'r':
    			motor.setSpeed(REEL_SPEED);
    			break;
    		case 's':
    			motor.setSpeed(shootSpd);
    		default:
    			motor.setSpeed(0);
    		}
    	}
    }
    
    public void setValve(int valveID, boolean on)
    {
    	valves[valveID].set(on);
    }
    
    public void toggleValve(int valve)
    {
    	switch(valve)
    	{
    	case 0:
    		setValve(0,!valves[0].get());
    		setValve(1,!valves[1].get());
    	case 1:
    		setValve(2,!valves[2].get());
    	}
    }
}
