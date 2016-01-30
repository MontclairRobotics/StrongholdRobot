package org.usfirst.frc.team555.robot;

public class DriveTrain {
	public static final int WHEELS_PER_SIDE = 2;
	public static final double ROT_CONSTANT = 1;
	
	private DriveMotor[] leftWheels, rightWheels;
	private double leftSpd, rightSpd;
	private char mode;
	private double distance;
	
	private static final boolean encoders=false;
	
	public static boolean shutdown = false;
	
	public DriveTrain()
	{
		leftWheels = new DriveMotor[WHEELS_PER_SIDE];
		rightWheels = new DriveMotor[WHEELS_PER_SIDE];
		for(int i=0; i<WHEELS_PER_SIDE; i++)
		{
			leftWheels [i]= new DriveMotor(i*2,encoders);
			rightWheels[i]= new DriveMotor(i*2+1,encoders);
		}
		for(DriveMotor motor : rightWheels) {
			motor.setInverted(true);
		}
	}
	
	public void setSpeedTank(double lSpd,double rSpd)
	{
		if (Control.halvingButtonPressed())
			lSpd /= 2.0; rSpd /= 2.0;
		
		leftSpd = lSpd;
		rightSpd = rSpd;
	}
	
	public void setSpeedArcade(double speed, double rotation)
	{
		if (Control.halvingButtonPressed())
			speed /= 2.0;
		
		rotation += 90;
		if(rotation < 0) rotation += 360;
		
		Robot.dashboard.putNumber("joystick-angle", rotation);
		
		int sign;
		if(rotation >= 180) {
			sign = -1;
			rotation = rotation-180;
		}
		else sign = 1;
		rotation = rotation/180;
		
		mode='s';
		leftSpd = speed*rotation*sign;
		rightSpd = speed*(1-rotation)*sign;
		Robot.dashboard.putNumber("leftSpeed", leftSpd);
		Robot.dashboard.putNumber("rightSpeed", rightSpd);
	}
	
	public void setDistance(double d,double speed,double rotation)
	{
		setSpeedArcade(speed,rotation);
		mode='d';
		distance=d;
		for(int i=0; i<leftWheels.length; i++)
		{
			leftWheels[i].resetDistance();
		}	
		for(int i=0; i<rightWheels.length; i++)
		{
			rightWheels[i].resetDistance();
		};
	}
	
	public void update()
	{
		/*if(driveModule.shutdown)
		{
			return;
		}*/
		if(mode=='d')
		{
			if(distance<=0)
			{
				setSpeedTank(0,0);
			}
			else
			{
				double sum=0;
				for(int i=0; i<leftWheels.length; i++)
				{
					sum+=leftWheels[i].getDistance();
				}	
				for(int i=0; i<rightWheels.length; i++)
				{
					sum+=rightWheels[i].getDistance();
				};
				distance-=sum/(leftWheels.length+rightWheels.length);
			}
		}
		for(int i=0; i<leftWheels.length; i++)
		{
			leftWheels[i].setSpeed(leftSpd);
			leftWheels[i].update();
		}	
		for(int i=0; i<rightWheels.length; i++)
		{
			rightWheels[i].setSpeed(rightSpd);
			rightWheels[i].update();
		};
	}
}
