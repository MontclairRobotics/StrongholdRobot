package org.usfirst.frc.team555.robot;

public class DriveTrain {
	public static final int WHEELS_PER_SIDE = 2;
	public static final double ROT_CONSTANT = 1;
	
	private Motor[] leftWheels, rightWheels;
	private double leftSpd, rightSpd;
	
	
	public static boolean shutdown = false;
	
	public DriveTrain()
	{
		leftWheels = new Motor[WHEELS_PER_SIDE];
		rightWheels = new Motor[WHEELS_PER_SIDE];
		for(int i=0; i<WHEELS_PER_SIDE; i++)
		{
			leftWheels [i]= new Motor('d',i*2);
			rightWheels[i]= new Motor('d',i*2+1);
		}
	}
	
	public void setSpeedTank(double lSpd,double rSpd)
	{
		leftSpd = lSpd;
		rightSpd = rSpd;
	}
	
	public void setSpeedArcade(double speed,double rotation)
	{
		leftSpd = speed+rotation*ROT_CONSTANT;
		rightSpd = speed-rotation*ROT_CONSTANT;
	}
	
	public void update()
	{
		/*if(driveModule.shutdown)
		{
			return;
		}*/
		for(int i=0; i<leftWheels.length; i++)
		{
			leftWheels[i].setSpeed(leftSpd);
			rightWheels[i].update();
		}	
		for(int i=0; i<rightWheels.length; i++)
		{
			leftWheels[i].setSpeed(rightSpd);
			rightWheels[i].update();
		}
	}
}
