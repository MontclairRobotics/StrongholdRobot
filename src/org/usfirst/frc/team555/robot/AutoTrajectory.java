package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

public class AutoTrajectory 
{
	
	
	private static final double velocityToPercentage=0.25;//TODO REAL VALUE
	private static final double maxRotation=30;//if set to 30, the correction will be 1 for 30, .5 for 15, ect.
	
	private static final String MAIN_KEY="RoboRealm";
	private static final String COORDS_KEY="Target";
	
	private static final double angleOfShooter=45;//degrees; 0 is facing ahead, 90 is facing ceiling
    private static final double heightOfTarget=2.5;//meters
    private static final double heightOfShooter=1;//meters
    private static final double g=9.8;//m/s 
    //private static final double powerToVelocity=1;//motor power to throw ball at one meter per second
    
    private static final double angleOfCamera=0;//degrees; 0 is facing ahead, 90 is facing ceiling
    private static final int windowHeight=240;//height of camera window, pixels
    private static final int windowWidth=320;//width of camera window, pixels
    private static final double FOVheight=57.6;//field of view of camera, degrees 
    private static final double FOVwidth=76.8;//field of view of camera, degrees
    private static final double heightOfCamera=1;//meters
	
	private static double y=heightOfTarget-heightOfShooter;
	private static double tan=Math.tan(Math.toRadians(angleOfShooter));
	private static double cos=Math.cos(Math.toRadians(angleOfShooter));
	
	private double velocity;
	private double angle;
	private NetworkTable table;
	
	public AutoTrajectory()
	{
		table=NetworkTable.getTable(MAIN_KEY);
	}
	
	public void update(double x,double y)
	{
		velocity=getVelocityFromPX(windowHeight-y);
		angle=Math.toDegrees(getAngleFromPixles(x,(double)windowWidth/2,(double)FOVwidth/2));
	}
	public void update(double y)
	{
		velocity=getVelocityFromPX(y);
	}
	public void update()
	{
		double[] coords=getNetworkTable();
		update(coords[0],coords[1]);
	}
	
	public double[] getNetworkTable()
	{
		double[] defaults={windowWidth/2,windowHeight/2};
		return table.getNumberArray(COORDS_KEY, defaults);
	}
	
	public double getVelocity()
	{
		return velocity;
	}
	public double getAngle()
	{
		return angle;
	}
	public double getSpeed()
	{
		return velocity*velocityToPercentage;
	}
	/*public double getRotation()
	{
		double rot = angle/maxRotation;
		if(rot>1)rot=1;
		if(rot<-1)rot=-1;
		return rot;
	}*/
	
	
	
	//========================================
	
	/*public static double getPXfromVelocity(double velocity)
	{
	
		double distance=getDistanceFromVelocity(velocity);
		double angle=getAngleFromDistance(distance);
		double PX=getPXfromAngle()
	}
	
	public static double getPXfromAngle(double PX,double halfWindow,double halfFOV)
	{
		
	}
	public static double getAngleFromDistance(double distance)
	{
		
	}
	public static double getDistanceFromVelocity(double velocity)
	{
		
	}*/
	
	public static double getVelocityFromPX(double heightPX)
	{
		double angle=getAngleFromPixles(heightPX,(double)windowHeight/2,(double)FOVheight/2)+Math.toRadians(angleOfCamera);
		double distance=getDistanceFromAngle(angle,heightOfTarget-heightOfCamera);
		return getVelocityFromDistance(distance);
	}
	public static double getAngleFromPixles(double PX,double halfWindow,double halfFOV)
    {
        return Math.atan((PX-halfWindow)/(halfWindow/Math.tan(halfFOV)));
    }
	public static double getDistanceFromAngle(double angle,double deltaY)
	{
		return deltaY/Math.tan(angle);
	}
	public static double getVelocityFromDistance(double x)
	{
	    double q = (-g*x*x)/(2*(y-x*tan));
		return Math.sqrt(q)/cos;
	}
}
