
/**
 * Calculates a trajectory
 * 
 * Given the power (or speed) of the shooter motor, the two functions can calculate either: 
 * -The two distances from the shooter when the ball will be at the correct height of the target,
 * -The two y values to display over the camera feed to show where the ball will pass through the target's height.
 * 
 * Jan 2016
 */
public class Trajectory
{
    private static final double angleOfShooter=45;//degrees; 0 is facing ahead, 90 is facing ceiling
    private static final double angleOfCamera=0;//degrees; 0 is facing ahead, 90 is facing ceiling
    private static final double heightOfTarget=2;//meters
    private static final double heightOfShooter=1;//meters
    private static final double heightOfCamera=1;
    private static final double g=9.8;//m/s 
    private static final double powerToVelocity=1;//motor power to throw ball at one meter per second
    private static final int cameraWindowHeight=500;//height of camera window, pixles
    private static final int cameraFOV=60;//field of view of camera, degrees
    
    private static final double deltaY=heightOfTarget-heightOfShooter;
    private static final double deltaYCamera=heightOfTarget-heightOfCamera;
    private static final double angleOfShooterRad=Math.toRadians(angleOfShooter);
    private static final double d = -g/(2*Math.pow(Math.cos(angleOfShooterRad),2));
    private static final double b = Math.tan(angleOfShooterRad);//tan theta
    private static final double c = -deltaY;//-delta y
        
    public static double[] getDistance(double power)
    {
        double velocity=power*powerToVelocity;
        double a = d/(velocity*velocity);
        
        double disc=b*b-4*a*c;
        if(disc<0)return null;
        double discRoot=Math.sqrt(disc);
        double[] distances={
            (-b+discRoot)/(2*a),
            (-b-discRoot)/(2*a)
        };
        return distances;
    }
    
    public static int[] getCameraY(double power)//0 at bottom and height at top
    {
        double[] distances=getDistance(power);
        if(distances==null) return null;
        double[] angles={
            Math.toDegrees(Math.atan2(deltaYCamera,distances[0])),
            Math.toDegrees(Math.atan2(deltaYCamera,distances[1]))
        };
        int[] values={
            (int)(cameraWindowHeight*((angles[0]-angleOfCamera)/cameraFOV+.5)),
            (int)(cameraWindowHeight*((angles[1]-angleOfCamera)/cameraFOV+.5))
        };
        return values;
    }
}
