package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Shooter {

	
	private ShooterMotor[] wheels;
    private DriveTrain driveTrain;
    private AutoTrajectory trajectory;
    private double speed=0.0;
    private boolean up=false;
    private boolean out=false;
    private boolean on=false;
    private boolean auto=false;
    private boolean manual=false;
    
    private double goalY=0.0;
    
    
    public static final double AJUST_FACTOR=0.25;
    public static final double TURN_FACTOR=0.25;
    
	private Valves valves;
	
	public Shooter(DriveTrain dt)
	{
		valves=new Valves();
		driveTrain=dt;
		trajectory=new AutoTrajectory();
		wheels=new ShooterMotor[2];
		for(int i=0;i<wheels.length;i++)
		{
			wheels[i]=new ShooterMotor(i);
		}
		wheels[1].setInverted(true);
	}
	public void setSpeed(double spd)
	{
		speed=spd;
	}
	
	public void setUp(boolean val)
	{
		if(val && !up)
		{
			up=true;
			valves.raise();
		}
	}
	
	public void setDown(boolean val)
	{
		if(val && up)
		{
			up=false;
			valves.lower();
		}
	}
	
	public void setOut(boolean val)
	{
		if(val && !out)
		{
			out=true;
			valves.shootOut();
		}
		else if(!val && out)
		{
			out=false;
			valves.shootIn();
		}
	}
	
	public void setOn(boolean val)
	{
		on=val;
	}
	
	public void setAuto(boolean val)
	{
		auto=val;
	}
	
	public void updateButtons()
	{
		setUp(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_UP));
		setDown(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_DOWN));
		setOut(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_TRIGGER));
		setOn(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_ACTIVE));
		setAuto(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_AUTOTARGET));
	}
	
	public void setJoystick(double x,double y,boolean manual)
    {
		this.manual=manual;
    	if(Math.abs(y)>Control.DEAD_ZONE)
    	{
    		goalY+=y*AJUST_FACTOR;
    	}
    	if(!driveTrain.isControlled && Math.abs(x)>Control.DEAD_ZONE)
    	{
    		driveTrain.setSpeedXY(x*TURN_FACTOR, 0, false,false);
    	}
    	if(Math.abs(y)>Control.DEAD_ZONE && manual)
    	{
    		setSpeed(y);
    	}
    	update();
    }
	
	public void update()
	{
		updateButtons();
		trajectory.update();
		if(auto)
		{
			//driveTrain.rotateTo(trajectory.getAngle());
			//speed=trajectory.getSpeed();
		}
		
		if(on||manual)
		{
			for(ShooterMotor motor : wheels) 
	    	{
	    		motor.setSpeed(speed);
	    	}
		}
		
		double[] autoCoords=trajectory.getNetworkTable();
		
		Robot.coordServer.setResponse("160,"+goalY+" "+(int)(autoCoords[0])+","+(int)(autoCoords[1]));
	}
}
