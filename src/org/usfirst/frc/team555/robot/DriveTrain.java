package org.usfirst.frc.team555.robot;
//import edu.wpi.first.wpilibj.PIDAllen;
//import edu.wpi.first.wpilibj.PIDController; 



public class DriveTrain {
	public static final int WHEELS_PER_SIDE = 2;
	public static final double ROT_CONSTANT = 1;
	public static final double DEAD_ZONE= .1;
	public static final double YAW_THRESHOLD = 5;
	public static final double YAW_CHANGE_FACTOR = 1;
	private static final double P_CORRECTION_FACTOR = .01;
	private static final double D_CORRECTION_FACTOR = 10*P_CORRECTION_FACTOR;
	private static final double I_CORRECTION_FACTOR = 0.0;//TODO fill in after P is found
	private static final int TIME_TO_DISABLE=5;//iterations until lock is deactivated
	
	public static final double WHEEL_CIRC = 8 * Math.PI;
	public static final int SLOW_CLICKS = 180;
	private double clicksRemaining = 0;
	private double prevClicks = 0;
	private boolean done = true;
	
	//private CourseLockPIDSource courseLockInput;
	private DriveMotor[] leftWheels, rightWheels;
	double leftSpd, rightSpd;
	private char mode;
	private double distance;
	
	public PID pid; //Jack's personal PIDController
	
	//private double totalError;
	public boolean backwards = false; 
	
	//private double prevAngle = 0;
	//private double angleChange = 0;
	
	//private int leftAdjustments = 0;
	//private int rightAdjustments = 0;
	
	public boolean isControlled;
	public boolean lock;
	public boolean shooterLock;
	
	private double angle=0;
	//private double lastAngle=0;
	//private double goalAngle=0;
	private int loopsSinceLastLock=TIME_TO_DISABLE;
	private double netSpd;
	
	private static final boolean encoders=false;
	
	public static boolean shutdown = false;
	
	public DriveTrain()
	{
		leftWheels = new DriveMotor[WHEELS_PER_SIDE];
		rightWheels = new DriveMotor[WHEELS_PER_SIDE];
		//source = new CourseLockPIDSource();
		//pidOut = new CourseLockPIDOut();
		pid = new PID(P_CORRECTION_FACTOR, I_CORRECTION_FACTOR,  D_CORRECTION_FACTOR);
		Robot.dashboard.putNumber("Kp", P_CORRECTION_FACTOR);
		Robot.dashboard.putNumber("Kd", D_CORRECTION_FACTOR);
		
		for(int i=0; i<WHEELS_PER_SIDE; i++)
		{
			leftWheels [i]= new DriveMotor(i*2,encoders); //1, 3
			rightWheels[i]= new DriveMotor(i*2+1,encoders); //2, 4
		}
		for(DriveMotor motor : rightWheels) {
			motor.setInverted(true);
		}
		//courseLockInput = new CourseLockPIDSource();
	}
	
	public void driveInches(double in) {
		clicksRemaining = in / WHEEL_CIRC * 360;
		prevClicks = getAvgEncoderClicks();
		done = false;
		
		Robot.dashboard.putString("auto", "DRIVE " + in + "in: initialized");
	}
	
	// TODO: driveFeet(double) derives from driveInches(double)
	public void driveFeet(double ft) {
		return driveInches(12*ft);
	}
	
	// TODO: drive(double, double) derives from driveInches(double)
	public void drive(double ft, double in) {
		return driveFeet(ft) + driveInches(in);
	}
	
	public boolean isDoneDriveInches()
	{
		return done;
	}
	
	public void driveInchesUpdate() {
		if (Robot.auto && !done) {
			clicksRemaining -= getAvgEncoderClicks() - prevClicks;
			prevClicks = getAvgEncoderClicks();
			
			if (clicksRemaining > 0) {
				double spd = clicksRemaining / SLOW_CLICKS + 0.25;
				leftSpd = rightSpd = spd;
			} else {
				//clicksRemaining = 0;
				leftSpd = rightSpd = 0;
				done = true;
				
				Robot.dashboard.putString("auto", "DRIVE: completed");
			}
		}
	}
	
	public double getAvgEncoderClicks() {
		double sum = 0;
		
		for (int i = 0; i < 2; i++) {
			sum += leftWheels[i].getDistance() + rightWheels[i].getDistance();
		}
		
		return sum / 4;
	}
	
	public void setSpeedTank(double lSpd,double rSpd)
	{
		if (Control.halvingButtonPressed())
			lSpd /= 2.0; rSpd /= 2.0;
		
		leftSpd = lSpd;
		rightSpd = rSpd;
	}
	
	public void setSpeedXY(double x, double y, boolean manual)
	{
		setSpeedXY(x,y);
		isControlled=manual;
	}
	
	public void setSpeedXY(double x, double y)
	{   
		x*=.75;
		if (backwards) {
			y = y*-1;
		}
		if (Math.abs(x)<Control.DEAD_ZONE)
		{

			lock=Control.getSlider(Control.DRIVE_STICK)<=0.5;
			if(Math.abs(y)<Control.DEAD_ZONE)
			{
				isControlled=false;
				leftSpd=0;
				rightSpd=0;
				netSpd=0;
			}
			else
			{
				isControlled=true;
				leftSpd=y;
				rightSpd=y;
				netSpd=y;
			}
		}
		else
		{
			lock=false;
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
	
	
	public void setLock(boolean userLock)
	{
		
		if(lock||userLock||shooterLock)
		{
			if(loopsSinceLastLock>=TIME_TO_DISABLE)
			{
				//courseLockInput.setTarget();
				pid.setTarget();
				Robot.dashboard.putString("Lock", "on");
			}
			
			courseLock(pid.get());
			loopsSinceLastLock=0;
		}
		else
		{
			pid.get();
			loopsSinceLastLock++;
			Robot.dashboard.putString("Lock", "off");
		}
	}
	
	public double courseLock(double correction)
	{		
		//double correction=angle*P_CORRECTION_FACTOR*(angle-lastAngle)*D_CORRECTION_FACTOR;
		//double correction = pid.get();
		
		if (netSpd > 0){
			if(1+correction > 0){
				rightSpd=netSpd*(1/(1+correction));	
			}
			else
			{
				rightSpd=0;
			}
			leftSpd=netSpd*(1+correction);
		}
		else {
			if(1+correction!=0){
				leftSpd=netSpd*(1/(1+correction));
			}
			else
			{
				leftSpd=0;
			}
			rightSpd=netSpd*(1+correction);
		}
		//lastAngle = angle;
		//Robot.dashboard.putString("PIDEnabled?", pid.isEnabled() ? "true" : "false" );
		Robot.dashboard.putNumber("Correction", correction);
		Robot.dashboard.putNumber("Error", pid.getError());
		pid.setPID(Robot.dashboard.getNumber("Kp"), I_CORRECTION_FACTOR, Robot.dashboard.getNumber("Kd"));
		Robot.dashboard.putNumber("Kp", pid.getP());
		Robot.dashboard.putNumber("Kd", pid.getD());
		return angle;
	}
	
	/**IF WE ARE HAVING A BAD PROBLEM AND EVERYTHING IS ON FIRE THIS IS THE PIDCONTROLLER LOGIC**/
	/*public void courseLock(double target){
		 double input = Robot.gyro.getYaw();
	     double result;
	     double error = target - input;
	     if (I_CORRECTION_FACTOR != 0) {
	            double potentialIGain = (totalError + error) * I_CORRECTION_FACTOR;
	            if (potentialIGain < 180) {
	              if (potentialIGain > -180) {
	                totalError += error;
	              } else {
	                totalError = -180 / I_CORRECTION_FACTOR;
	              }
	            } else {
	              m_totalError = m_maximumOutput / m_I;
	            }
	          }

	          m_result = m_P * m_error + m_I * m_totalError +
	                     m_D * (m_error - m_prevError) + calculateFeedForward();
	        }
	        m_prevError = m_error;
	     
	}*/
	
	public void update()
	{
		/*if(driveModule.shutdown)
		{
			return;
		}*/
		if (Robot.auto) {
			driveInchesUpdate();
		}
		
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
		//lastAngle=angle;
		
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
	
	public void rotateTo(double target)
	{
		if(isControlled)
		{
			pid.setTarget(target,false);
			shooterLock=true;
		}
		else
		{
			pid.setTarget(0.0,false);
			shooterLock=false;
		}
	}
}
