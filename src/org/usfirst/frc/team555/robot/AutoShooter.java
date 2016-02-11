package org.usfirst.frc.team555.robot;

public class AutoShooter 
{
	private static final double ANGLE_CORRECTION = 0.01;
	private static final double SPEED_CORRECTION = 0.5;
	private DriveTrain drive;
	private AutoTrajectory trajectory;
	private Shooter shooter;
	
	private char mode;
	
	public AutoShooter(DriveTrain d,Shooter s)
	{
		drive=d;
		shooter=s;
		trajectory=new AutoTrajectory();
		mode='s';
	}
	public void target(boolean on)
	{
		if(on)
		{
			mode='t';
		}
		else
		{
			mode='s';
		}
	}
	public void activate()
	{
		mode='t';
	}
	public void stop()
	{
		mode='s';
	}
	public void update()
	{
		if(mode=='t')
		{
			trajectory.update();
			drive.setSpeedXY(0,trajectory.getAngle()*ANGLE_CORRECTION);
			shooter.setSpeed(trajectory.getSpeed()*SPEED_CORRECTION);
		}
	}
}
