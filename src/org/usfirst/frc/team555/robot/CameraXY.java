
/**
 * CameraXY
 * 
 * Calculates the X and Y coordinates to overlay on the camera image
 * 
 * Usage:
 * once per loop call update(double power,double rotate)
 * once per camera frame refresh call refresh()
 * 
 * getX() and getY1() and getY2() for x and y
 * getTopY1() and getTopY2 for y starting at the top of display and increasing downwards
 */
public class CameraXY
{   
    public static final double angleOfCamera=15;//degrees; 0 is facing ahead, 90 is facing ceiling
    public static final int windowHeight=500;//height of camera window, pixles
    public static final int windowWidth=600;//width of camera window, pixles
    public static final int FOVheight=60;//field of view of camera, degrees 
    public static final int FOVwidth=60;//field of view of camera, degrees
    public static final double heightOfCamera=1;//meters
    
    private static int x,y1,y2;
    private static double resetRot;

    public static void update(double power, NavXGyro gyro)//shooter power and rotation in degrees since last update
    {
        double[] angles = Trajectory.getDegrees(power);
        if(angles==null)
        {
            y1=0;
            y2=0;
        }
        else
        {
            y1=getPixles(angles[0]-angleOfCamera,windowHeight,FOVheight);
            y2=getPixles(angles[1]-angleOfCamera,windowHeight,FOVheight);
        }
        
        x=getPixles(gyro.getZ()-resetRot,windowWidth,FOVwidth);
    }
    
    public static void refresh(NavXGyro gyro)
    {
        resetRot=gyro.getZ();
        x=0;
    }
    
    public static int getX()
    {
        return x;
    }
    public static int getY1()
    {
        return y1;
    }
    public static int getY2()
    {
        return y2;
    }
    public static int getTopY1()
    {
        return windowHeight-y1;
    }
    public static int getTopY2()
    {
        return windowHeight-y2;
    }
    
    public static int getPixles(double angle,int window,int FOV)
    {
        return (int)(window*(angle/FOV+.5)+.5);
    }
}
