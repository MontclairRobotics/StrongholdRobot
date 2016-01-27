package org.usfirst.frc.team555.robot;

public class AutoTrajectory 
{
	private static final double angleOfShooter=45;//degrees; 0 is facing ahead, 90 is facing ceiling
    private static final double heightOfTarget=2.5;//meters
    private static final double heightOfShooter=1;//meters
    private static final double g=9.8;//m/s 
    private static final double powerToVelocity=1;//motor power to throw ball at one meter per second
    
    public static final double angleOfCamera=15;//degrees; 0 is facing ahead, 90 is facing ceiling
    public static final int windowHeight=500;//height of camera window, pixels
    public static final int windowWidth=600;//width of camera window, pixels
    public static final int FOVheight=60;//field of view of camera, degrees 
    public static final int FOVwidth=60;//field of view of camera, degrees
    public static final double heightOfCamera=1;//meters
	
	private double g=9.8;
	private double y=heightOfTarget-heightOfShooter;
	private double tan=Math.tan(Math.toRadians(angleOfShooter));
	private double cos=Math.cos(Math.toRadians(angleOfShooter));
	
	public static getVelocity(int heightPX)
	{
		return getVX(getPX(heightPX,windowHeight,FOVwidth,angleOfCamera));
	}
	
	public static getVX(double x)
	{
		return Math.sqrt((-g*x*x)/(2*(y-x*tan)))/cos;
	}
	
	public double getPX(int PX,int window,int FOV,double cameraAngle)//gets angle from pixles
    {
        return Math.atan((double)(PX-(Window/2))/((((double)Window)/2)/Math.tan(FOV/2);
    }
}
