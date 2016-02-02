package org.usfirst.frc.team555.robot;

public class DriveTrain {
	public static final int WHEELS_PER_SIDE = 2;
	public static final double ROT_CONSTANT = 1;
	public static final double YAW_THRESHOLD = 5;
	public static final double YAW_CHANGE_FACTOR = 0.01;
	
	private DriveMotor[] leftWheels, rightWheels;
	private double leftSpd, rightSpd;
	private char mode;
	private double distance;
	
	private double prevAngle = 0;
	private double angleChange = 0;
	
	private int leftAdjustments = 0;
	private int rightAdjustments = 0;
	
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
	
	/*public void setSpeedArcade(double speed, double rotation)
	{
		if(speed > 1) speed = 1;
		if (Control.halvingButtonPressed())
			speed /= 2.0;
		
		rotation += 90;
		if(rotation < 0) rotation += 360;
		
		
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
		
		Robot.dashboard.putNumber("before-left", leftSpd);
		Robot.dashboard.putNumber("before-right", rightSpd);
		
		double factor = 0.0;
		if(Math.abs(leftSpd) > Math.abs(rightSpd)) {
			factor = Math.abs(speed/leftSpd);
		} else {
			factor = Math.abs(speed/rightSpd);
		}
		leftSpd = leftSpd * factor;
		rightSpd = rightSpd * factor;
		
		
		Robot.dashboard.putNumber("leftSpeed", leftSpd);
		Robot.dashboard.putNumber("rightSpeed", rightSpd);
	}*/
	
	public void setSpeedXY(double x, double y)
	{   
		if(x==0&&y==0)
		{
			leftSpd=0;
			rightSpd=0;
		}
		else{
			double max;
			if(Math.abs(x)>=Math.abs(y))
			{
				max=1+Math.abs(y/x);
			}
			else
			{
				max=1+Math.abs(x/y);
			}
	        leftSpd=(y+x)/max;
	        rightSpd=(y-x)/max;
		}
		if(Control.halvingButtonPressed()) {
			leftSpd /= 2;
			rightSpd /= 2;
		}
		Robot.dashboard.putNumber("leftSpeed", leftSpd);
		Robot.dashboard.putNumber("rightSpeed", rightSpd);
	}
	/*public void setSpeedAllen(double x, double y)
	{
		if(x==0 && y==0) {
			leftSpd = 0;
			rightSpd = 0;
		}
		else{
			double Max = 1.0;
			if (x>y){
				Max = Math.abs(Math.abs(y/x)+1);
			}
			else{
				Max = Math.abs(Math.abs(x/y)+1);
			}
			leftSpd=(y+x)/Max;
	        rightSpd=(y-x)/Max;
		}
	}*/
	
	public void setDistance(double d,double speed,double rotation)
	{
		setSpeedXY(0, speed);
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
		angleChange = Robot.gyro.getYaw() - prevAngle;
		//Gyro adjustments
		if(Control.getDegrees(Control.DRIVE_STICK) < 10 && Math.abs(angleChange) > YAW_THRESHOLD) {
			rightAdjustments++;
			rightSpd *= 1+(YAW_CHANGE_FACTOR*rightAdjustments);
		} else {
			rightAdjustments = 0;
		}
		if(Control.getDegrees(Control.DRIVE_STICK) > 360-10 && Math.abs(angleChange) > YAW_THRESHOLD) {
			leftAdjustments++;
			leftSpd *= 1+(YAW_CHANGE_FACTOR*leftAdjustments);
		} else {
			leftAdjustments = 0;
		}
		prevAngle = Robot.gyro.getYaw();
		
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
