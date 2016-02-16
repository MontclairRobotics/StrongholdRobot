package org.usfirst.frc.team555.robot;

public class AutoShooter 
{
	private static final double SPEED_CORRECTION = 0.5;
	private DriveTrain drive;
	private AutoTrajectory trajectory;
	private ManualShooter shooter;
	
	private char mode;
	
	public AutoShooter(ManualShooter s)
	{
		drive=s.driveTrain;
		shooter=s;
		trajectory=new AutoTrajectory();
		mode='s';
	}
	public void setActive(boolean on)
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
		if(mode=='t'&&!drive.isControlled)
		{
			trajectory.update();
			/*if(drive.source.pidGet())));
			{
				shooter.setSpeed(trajectory.getSpeed()*SPEED_CORRECTION);
			}*/
		}
	}
}
