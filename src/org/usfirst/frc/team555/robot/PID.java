package org.usfirst.frc.team555.robot;

/*
 * Usage:
 * 
 * Create with P,I,D values
 * Call setTarget() to set the target
 * Call get() to get the correction
 * 
 * It just uses the math from the WPI PIDController class.
 * 
 */

public class PID {

	double P,I,D,minOut,maxOut;
	
	double target;
	double totalError,prevError,error;
	private double angle;
	private double out;
	
	public static final boolean gyroEnabled=true;
	
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
		this(P,I,D,-10.0,10.0);
	}
	
	public void setTarget(double t)
	{
		target=getCurrentVal()+t;
		error=0.0;
		prevError=0.0;
		totalError=0.0;
	}
	
	public void setTargetNonReset(double t)
	{
		target=getCurrentVal()+t;
	}
	
	public void setPID(double P, double I, double D){
		this.P=P;
		this.I=I;
		this.D=D;
	}
	public void setMinMaxOut(double min, double max){
		minOut = min;
		maxOut = max;
	}
	
	public void setTarget()
	{
		setTarget(0.0);
	}

	public double get()
	{
		
		return out;
	}
	
	private double getCurrentVal()//make this the input value
	{
		if(gyroEnabled)
		{
			return Robot.gyro.getYaw();
		}
		else
		{
			return 0.0;
		}
	}
	
	public void calculate()
	{
		error=target-getCurrentVal();
		error=((error+180)%360+360)%360-180;
		//error=target-angle;
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
	
		out = P * error + I * totalError +
	             D * (error - prevError); //+ calculateFeedForward();

		prevError = error;
		
		if (out > maxOut) out=maxOut;
		else if (out < minOut) out=minOut;
	}
	public double getError(){
		return error;
	}
	public double getP(){
		return P;
	}
	public double getD(){
		return D;
	}
	public double getI(){
		return I;
	}
}