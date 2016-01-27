
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
    public static final int windowHeight=500;//height of camera window, pixels
    public static final int windowWidth=600;//width of camera window, pixels
    public static final int FOVheight=60;//field of view of camera, degrees 
    public static final int FOVwidth=60;//field of view of camera, degrees
    public static final double heightOfCamera=1;//meters
    
    private int x,y1,y2;
    private double resetRot;

    public void update(double power, double gyro)//shooter power and z rotation in degrees
    {
        double[] angles = Trajectory.getDegrees(power);
        if(angles==null)
        {
            y1=0;
            y2=0;
        }
        else
        {
            y1=getPixels(angles[0]-angleOfCamera,windowHeight,FOVheight);
            y2=getPixels(angles[1]-angleOfCamera,windowHeight,FOVheight);
        }
        
        x=getPixels(gyro-resetRot,windowWidth,FOVwidth);
    }
    
    public void refresh(double gyro)//z rotation
    {
        resetRot=gyro;
        x=0;
    }
    
    public int getX()
    {
        return x;
    }
    public int getY1()
    {
        return y1;
    }
    public int getY2()
    {
        return y2;
    }
    public int getTopY1()
    {
        return windowHeight-y1;
    }
    public int getTopY2()
    {
        return windowHeight-y2;
    }
    
    public int getPixels(double angle,int window,int FOV)
    {
        return (int)(window*(angle/FOV+.5)+.5);
    }
}
