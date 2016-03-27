package org.usfirst.frc.team555.robot;

public class Shooter {

	
	private ShooterMotor[] wheels;
    private DriveTrain driveTrain;
    private AutoTrajectory trajectory;
    private double speed=0.0;
    private boolean up=false;
    private boolean out=false;
    private boolean auto_shoot_on=false;
    private boolean auto_align=false;
    private boolean half=false;
    private boolean manual=false;
    private PID pid;
    
    private double goalY=0.0;
    
    
    public static final double AJUST_FACTOR=0.25;
    public static final double TURN_FACTOR=0.25;
    
    public static double P_CORRECTION_FACTOR = 0.0;
    public static double I_CORRECTION_FACTOR = 0.0;
    public static double D_CORRECTION_FACTOR = 0.0;
    
    public static final boolean encoders = false;
    
	private Valves valves;
	
	public Shooter(DriveTrain dt)
	{
		valves = new Valves();
		driveTrain = dt;
		trajectory = new AutoTrajectory();
		wheels = new ShooterMotor[2];
		for(int i = 0; i < wheels.length; i++)
		{
			wheels[i] = new ShooterMotor(i, encoders);
		}
		wheels[1].setInverted(true);
		
		pid = new PID(P_CORRECTION_FACTOR, I_CORRECTION_FACTOR, D_CORRECTION_FACTOR,-1.0, 1.0, -1.0, 1.0);
	}
	public void setSpeed(double spd)
	{
		speed=spd;
	}
	
	public void setUp(boolean val)//raise arm
	{
		if(val && !up)
		{
			up=true;
			valves.raise();
		}
	}
	
	public void setDown(boolean val)//lower arm
	{
		if(val && up)
		{
			up=false;
			valves.lower();
			halfUp(true);
		}
	}
	/*
	public void setOneUp(boolean val)
	{
		if(val && !up)
		{
			up=true;
			valves.raiseOne();
		}
	}
	
	public void setOneDown(boolean val)
	{
		if(val && up)
		{
			up=false;
			valves.lowerOne();
		}
	}
	*/
	public void halfUp(boolean val)//raise half
	{
		if(val && !half)
		{
			half=true;
			valves.halfOn();
		}
	}
	
	public void halfDown(boolean val)//lower half
	{
		if(val && half)
		{
			half=false;
			valves.halfOff();
		}
	}
	
	public void setOut(boolean val)//shoot out
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
		auto_shoot_on=val;
	}
	
	public void setAuto(boolean val)
	{
		auto_align=val;
		goalY=trajectory.getNetworkTable()[1];
	}
	
	public void updateButtons()
	{
		setUp(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_UP));
		setDown(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_DOWN));
		setOut(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_TRIGGER));
		setOn(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_AUTO_ACTIVE));
		setAuto(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_AUTOTARGET));
		halfUp(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_HALF_UP));
		halfDown(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_HALF_DOWN));
		setWheelsIntake(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_INTAKE_MOTORS_ON));
		setWheelsShoot(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_SHOOT_MOTORS_ON));
		//setReset(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_RESET));
		//setOneUp(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_UP_ONE));
		//setOneDown(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_DOWN_ONE));
		
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
    	/*if(Math.abs(y)>Control.DEAD_ZONE && manual)
    	{
    		if(y > 0) setWheelsShoot(true);
    		else setWheelsIntake(true);
    	}*/
    	//update();
    }
	
	public void setReset(boolean val) {
		if(val) valves.resetShooterPush();
	}
	
	public void setWheelsIntake(boolean val)
	{
		if(!val)return;
		manual=true;
		setWheels(-0.8);
		
	}
	
	public void setWheelsShoot(boolean val)
	{
		if(!val)return;
		manual=true;
		setWheels(0.8);
		
	}
	public void setWheels(double spd) {
		if(encoders) {
			pid.setTarget(wheels[0].getRate(),false);
			double correction = pid.get(wheels[1].getRate());
			double correctionPercent = correction / wheels[1].getRate();
			wheels[0].setSpeed(spd);
			wheels[1].setSpeed(spd*correctionPercent);
		} else {
			wheels[0].setSpeed(spd);
			wheels[1].setSpeed(spd);
		}
	}
	/*
	public void setWheelsIntake(boolean val) {
		if(!val) return;
		if(encoders) {
			pid.setTarget(wheels[0].getRate(),false);
			double correction = pid.get(wheels[1].getRate());
			double correctionPercent = correction / wheels[1].getRate();
			wheels[0].setSpeed(-0.8);
			wheels[1].setSpeed(-0.8*correctionPercent);
		} else {
			wheels[0].setSpeed(-0.8);
			wheels[1].setSpeed(-0.8);
		}
	}*/
	
	public void updateHTTP()
	{
		double[] autoCoords=trajectory.getNetworkTable();	
		Robot.coordServer.setResponse("0"+","+goalY+","+autoCoords[0]+","+autoCoords[1]);
	}
	
	public void update()
	{
		updateButtons();
		trajectory.update(goalY);
		if(!manual)
		{
			if(auto_align)
			{
				driveTrain.rotateTo(trajectory.getAngle());
			}
			if(auto_shoot_on)
			{
				speed=trajectory.getSpeed();
			}
			else
			{
				speed=0.0;
			}
			setWheels(speed);
		}
		for(ShooterMotor motor : wheels) 
    	{
    		motor.update();
    	}
		
		updateHTTP();
		manual=false;
	}
}
