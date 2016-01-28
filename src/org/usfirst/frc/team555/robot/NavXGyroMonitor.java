/*package org.usfirst.frc.team555.robot;

import com.kauailabs.navx.frc.AHRS;
import com.ni.vision.NIVision.Range;

public class NavXGyroMonitor {
    
    private static final DoubleRange FORWARD_INTERVAL =   new Range(-45.0, 45.0); // Robot is facing the enemy tower
    private static final DoubleRange LEFT_INTERVAL =      new Range(45.0, 135.0); // Robot is facing driver's left
    private static final DoubleRange RIGHT_INTERVAL =     new Range(-135.0, -45.0); // Robot is facing driver's right
    private static final DoubleRange BACKWARD_INTERVAL =  new Range.between(-45.0, 45.0); // Robot is facing drivers station
    
    private static final DoubleRange NOMINAL_INTERVAL =       new DoubleRange(0.0, 5.0); // This represents whatever difference might be caused by regular jostling 
    private static final DoubleRange INCLINED_INTERVAL =      new DoubleRange(5.0, 20.0);
    private static final DoubleRange NEAR_FATAL_INTERVAL =    new DoubleRange(20.0, 30.0); // TODO: - Replace 30° with whatever the tipping point of the robot is
    private static final DoubleRange FATAL_INTERVAL =         new DoubleRange(30.0, 180.0);
    
    public enum Direction {
        FORWARD, LEFT, RIGHT, BACKWARD;
    }
    
    public enum Tilt {
        NOMINAL, INCLINED, NEAR_FATAL, FATAL;
    }
    
    NavXGyro gyro; // Reference to NavX gyroscope
    
    public NavXGyroMonitor(NavXGyro gyro) {
        this.gyro = gyro;
    }
    
    public void display() {
        SmartDashboard.putString("Direction", getDirection().toString() + 
            "\n\tZ-axis: " + String.valueOf(gyro.getZ()) + "°");
        SmartDashboard.putString("Tilt", getTilt().toString() + 
            "\n\tX-axis: " + String.valueOf(gyro.getX()) + "°, Y-axis: " + String.valueOf(gyro.getY()) + "°");
    }
    
    public Direction getDirection() {
        double zValue = gyro.getZ();
        
        if (FORWARD_INTERVAL.containsDouble(zValue))
            direction = Direction.FORWARD;
        else if (LEFT_INTERVAL.containsDouble(zValue))
            direction = Direction.LEFT;
        else if (RIGHT_INTERVAL.containsDouble(zValue))
            direction = Direction.RIGHT;
        else if (BACKWARD_INTERVAL.containsDouble(zValue))
            direction = Direction.BACKWARD;
    }
    
    public Tilt getTilt() {
        double xMag = Math.abs(gyro.getX());
        double yMag = Math.abs(gyro.getY());
        double greatestTilt = (xMag > yMag) ? xMag : yMag;
        
        if (NOMINAL_INTERVAL.containsDouble(greatestTilt))
            tilt = Tilt.NOMINAL;
        else if (INCLINED_INTERVAL.containsDouble(greatestTilt))
            tilt = Tile.INCLINED;
        else if (NEAR_FATAL_INTERVAL.containsDouble(greatestTilt))
            tilt = Tilt.NEAR_FATAL;
        else if (FATAL_INTERVAL.containsDouble(greatestTilt))
            tilt = Tilt.FATAL;
    }
    
}
*/