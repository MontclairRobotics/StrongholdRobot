package org.usfirst.frc.team555.robot;
import edu.wpi.first.wpilibj.PIDController; 



public class DriveTrain {
	public static final int WHEELS_PER_SIDE = 2;
	public static final double ROT_CONSTANT = 1;
	public static final double DEAD_ZONE= .1;
	public static final double YAW_THRESHOLD = 5;
	public static final double YAW_CHANGE_FACTOR = 1;
	private static final double P_CORRECTION_FACTOR = 0.02;
	private static final double D_CORRECTION_FACTOR = 0.02;
	private static final double I_CORRECTION_FACTOR = 0.0;//TODO fill in after P is found
	private static final int TIME_TO_DISABLE=15;//iterations until lock is deactivated
	
	private CourseLockPIDSource courseLockInput;
	private DriveMotor[] leftWheels, rightWheels;
	double leftSpd, rightSpd;
	private char mode;
	private double distance;
	//private PIDController pid = new PIDController(P_CORRECTION_FACTOR, D_CORRECTION_FACTOR, I_CORRECTION_FACTOR, );
	
	//private double prevAngle = 0;
	//private double angleChange = 0;
	
	//private int leftAdjustments = 0;
	//private int rightAdjustments = 0;
	
	public boolean isControlled;
	
	private double angle=0;
	private double lastAngle=0;
	private double goalAngle=0;
	private int loopsSinceLastLock=TIME_TO_DISABLE;
	private double netSpd;
	
	private static final boolean encoders=false;
	
	public static boolean shutdown = false;
	
	public DriveTrain()
	{
		leftWheels = new DriveMotor[WHEELS_PER_SIDE];
		rightWheels = new DriveMotor[WHEELS_PER_SIDE];
		for(int i=0; i<WHEELS_PER_SIDE; i++)
		{
			leftWheels [i]= new DriveMotor(i*2,encoders); //1, 3
			rightWheels[i]= new DriveMotor(i*2+1,encoders); //2, 4
		}
		for(DriveMotor motor : rightWheels) {
			motor.setInverted(true);
		}
		courseLockInput = new CourseLockPIDSource();
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
	
	public void setSpeedXY(double x, double y, boolean manual)
	{
		setSpeedXY(x,y);
		isControlled=manual;
	}
	
	public void setSpeedXY(double x, double y)
	{   
		x*=.75;
		if(Math.abs(x)<Control.DEAD_ZONE&&Math.abs(y)<Control.DEAD_ZONE)
		{
			isControlled=false;
			leftSpd=0;
			rightSpd=0;
			netSpd=0;
		}
		else if (Math.abs(x)<Control.DEAD_ZONE)
		{
			isControlled=true;
			leftSpd=y;
			rightSpd=y;
			netSpd=y;
		}
		else
		{
			isControlled=true;
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
	        netSpd=y;
		}
		if(Control.halvingButtonPressed()) {
			leftSpd /= 2;
			rightSpd /= 2;
			netSpd /= 2;
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
	
	
	public void setLock(boolean lock)
	{
		if(lock)
		{
			if(loopsSinceLastLock>=TIME_TO_DISABLE)
			{
				courseLockInput.setTarget();
			}
			
			/*courseLock(goalAngle);
            loopsSinceLastLock=0;*/
		}
		else
		{
			loopsSinceLastLock++;
		}
	}
	
	public double courseLock(double target)
	{
		
		//double correction=angle*P_CORRECTION_FACTOR*(angle-lastAngle)*D_CORRECTION_FACTOR;
		leftSpd=netSpd-correction;
		rightSpd=netSpd+correction;
		lastAngle = angle;
		return angle;
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
		lastAngle=angle;
		/*
		double yaw = Robot.gyro.getYaw();
		if(yaw > 180) yaw = -360+yaw;
		angleChange = Robot.gyro.getYaw() - prevAngle;
		//Gyro adjustments
		if(Control.getDegrees(Control.DRIVE_STICK) < 10 && Math.abs(angleChange) > YAW_THRESHOLD) 
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
		prevAngle = Robot.gyro.getYaw();*/
		
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
