package org.usfirst.frc.team555.robot;

public class AutoShooter 
{
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
			drive.setSpeedArcade(0.0,trajectory.getAngle());
			shooter.setSpeed(trajectory.getSpeed());
		}
	}
}
