package org.usfirst.frc.team555.robot;


public class PID {

	double P,I,D,minOut,maxOut;
	
	double target;
	double totalError,prevError,error;
	
	public PID(double P,double I,double D,double minOut,double maxOut)
	{
		this.P=P;
		this.I=I;
		this.D=D;
		this.minOut=minOut;
		this.maxOut=maxOut;
		setTarget();
	}
	
	public PID(double P,double I,double D)
	{
		this(P,I,D,-1.0,1.0);
	}
	
	public void setTarget(double t)
	{
		target=getCurrentVal()+t;
	}
	public void setTarget()
	{
		setTarget(0.0);
	}

	public double get()
	{
		double angle=getCurrentVal()-target;
		angle=((angle+180)%360+360)%360-180;
		return calculate(angle);
	}
	
	private double getCurrentVal()//make this the input value
	{
		return Robot.gyro.getYaw();
	}
	
	private double calculate(double input)
	{
		error=target-input;
		totalError+=error;
		if (I != 0) 
		{
			double potentialIGain = (error+totalError) * I;
			if (potentialIGain < maxOut) {
				if (potentialIGain > minOut) {
					totalError += error;
				} else {
					totalError = minOut / I;
				}
			} else {
				totalError = maxOut / I;
			}
		}
	
		double out = P * error + I * totalError +
	             D * (error - prevError); //+ calculateFeedForward();

		prevError = error;
		
		if (out > maxOut) out=maxOut;
		else if (out < minOut) out=minOut;
		
		return out;
	}
}
