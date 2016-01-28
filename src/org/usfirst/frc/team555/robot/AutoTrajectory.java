package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

public class AutoTrajectory 
{
	private static final String MAIN_KEY="RoboRealm";
	private static final String KEY="BLOBS";
	
	private static final double angleOfShooter=45;//degrees; 0 is facing ahead, 90 is facing ceiling
    private static final double heightOfTarget=2.5;//meters
    private static final double heightOfShooter=1;//meters
    private static final double g=9.8;//m/s 
    //private static final double powerToVelocity=1;//motor power to throw ball at one meter per second
    
    public static final double angleOfCamera=15;//degrees; 0 is facing ahead, 90 is facing ceiling
    public static final int windowHeight=500;//height of camera window, pixels
    public static final int windowWidth=600;//width of camera window, pixels
    public static final int FOVheight=60;//field of view of camera, degrees 
    public static final int FOVwidth=60;//field of view of camera, degrees
    public static final double heightOfCamera=1;//meters
	
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
	
	public void update(int x,int y)
	{
		velocity=getVelocityFromPX(y);
		angle=Math.toDegrees(getAngleFromPixles(x,(double)windowWidth/2,(double)FOVwidth/2));
	}
	public void update()
	{
		double[] defaults={windowWidth/2,windowHeight/2};
		table.getNumberArray(KEY, defaults);
		int x,y;
	}
	public double getTargetVelocity()
	{
		return velocity;
	}
	public double getAngle()
	{
		return angle;
	}
	
	public static double getVelocityFromPX(int heightPX)
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
		return Math.sqrt((-g*x*x)/(2*(y-x*tan)))/cos;
	}
}
