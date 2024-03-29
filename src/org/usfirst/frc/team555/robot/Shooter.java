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
    
    
    public static final double AJUST_FACTOR=25;
    public static final double TURN_FACTOR=0.25;
    
    public static double P_CORRECTION_FACTOR = 0.0;
    public static double I_CORRECTION_FACTOR = 0.0;
    public static double D_CORRECTION_FACTOR = 0.0;
    
    public static final boolean encoders = false;
    
    public boolean halfExtended = true;
    public boolean flopButtonPressed = false;
    
	public Valves valves;
	
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
	
	public void setArm(boolean val) {
		if(val) {
			valves.raiseArm();
		} else {
			valves.lowerArm();
		}
	}
	
	public void armUp(boolean buttonVal) {
		if(buttonVal) {
			setArm(true);
		}
	}
	
	public void armDown(boolean buttonVal) {
		if(buttonVal) {
			setArm(false);
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
		if(val) setHalf(true);
	}
	
	public void halfDown(boolean val)//lower half
	{
		if(val) setHalf(false);
	}
	
	public void setHalf(boolean val) {
		if(val) {
			valves.halfOff();
			halfExtended = true;
		} else {
			valves.halfOn();
			halfExtended = false;
		}
	}
	
	public void setOut(boolean val)//shoot out
	{
		if(val)
		{
			valves.shootOut();
		}
		else if(!val)
		{
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
		armUp(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_UP));
		armDown(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_DOWN));
		setOut(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_TRIGGER));
		setOn(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_AUTO_ACTIVE));
		setAuto(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_AUTOTARGET));
		halfUp(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_HALF_UP));
		halfDown(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_HALF_DOWN));
		//setHalf(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_HALF_DOWN));
		setWheelsIntake(Control.getButton(Control.SHOOT_STICK, Control.SHOOT_INTAKE_MOTORS_ON));
		setWheelsShoot(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_SHOOT_MOTORS_ON));
		//Robot.dashboard.putString("Half-extended", Boolean.toString(halfExtended));
		flop(Control.getButton(Control.DRIVE_STICK, Control.CAM_SWITCH));
		
	}
	
	public void setJoystick(double x,double y,boolean manual)
    {
		this.manual=manual;
    	if(Math.abs(y)>Control.DEAD_ZONE)
    	{
    		goalY+=y*AJUST_FACTOR;
    		if(goalY>AutoTrajectory.windowHeight)goalY=AutoTrajectory.windowHeight;
    		if(goalY<0)goalY=0;
    	}
    	if(!driveTrain.isControlled && Math.abs(x)>Control.DEAD_ZONE)
    	{
    		driveTrain.setSpeedXY(x*TURN_FACTOR, 0, false,false,true);
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
		setWheels(-0.7);
		
	}
	
	public void setWheelsShoot(boolean val)
	{
		if(!val)return;
		manual=true;
		//setWheels(0.475);
		//if(valves.isArmUp()) {
			setWheels(0.555);
			//Robot.dashboard.putString("arm-status", "arm up speed");
		//} else {-
			//setWheels(0.4);
			//Robot.dashboard.putString("arm-status", "arm down speed");
		//}
		
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
		if(Robot.coordServer!=null)
			Robot.coordServer.setResponse(AutoTrajectory.windowWidth/2+","+goalY+","+autoCoords[0]+","+autoCoords[1]);
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
	
	public void flop(boolean val) {
		if(Robot.cameraServer != null && val && !flopButtonPressed) {
			flopButtonPressed = true;
			Robot.cameraServer.swap();
		} else if(flopButtonPressed && !val) {
			flopButtonPressed = false;
		}
	}
	
}
