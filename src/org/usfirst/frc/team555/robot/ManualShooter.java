package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class ManualShooter 
{

	private ShooterMotor[] wheels;
    private Solenoid[] valves;
    
    private double speed;
    private boolean up;
    public DriveTrain driveTrain;
    
    public static final double REEL_SPEED=-0.5;
    public static final double POWER_CHANGE=0.05;
    
    public ManualShooter(DriveTrain d)
    {
    	wheels= new ShooterMotor[2];
    	for(int i = 0; i < Map.SHOOTER_MOTORS.length; i++)
    	{
    		wheels[i] = new ShooterMotor(i);
    	}
    	wheels[1].setInverted(true);

        valves=new Solenoid[2];
        for(int i=0;i<Map.SHOOTER_SOLENOID_PORTS.length;i++)
        {
        	valves[i] = new Solenoid(Map.SHOOTER_SOLENOID_PORTS[i]);
        }
        
        driveTrain=d;
    }
    
    public void setSpeed(double s)
    {
    	speed=s;
    }
    
    public void update()
    {
    	for(ShooterMotor motor : wheels) 
    	{
    		motor.setSpeed(speed);
    	}
    }
    
    public void raise()
    {
    	//FILL IN
    }
    
    public void lower()
    {
    	//FILL IN
    }
    
    public void setLift(boolean goUp,boolean goDown)
    {
    	if(goUp && !up)
    	{
    		up=true;
    		raise();
    	}
    	else if(goDown && up)
    	{
    		up=false;
    		lower();
    	}
    }
    
    public void setJoystick(double x,double y)
    {
    	if(Math.abs(y)>Control.DEAD_ZONE)
    	{
    		speed=y;
    	}
    	if(!driveTrain.isControlled && Math.abs(x)>Control.DEAD_ZONE)
    	{
    		driveTrain.setSpeedXY(x/2, 0, false);
    	}
    }
}
