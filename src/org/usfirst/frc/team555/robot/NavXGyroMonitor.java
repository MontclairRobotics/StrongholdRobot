/*package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavXGyroMonitor {
    
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
        
        if (zValue >= -45.0 && zValue < 45.0)
            return Direction.FORWARD;
        else if (zValue >= 45.0 && zValue < 135.0)
            return Direction.LEFT;
        else if (zValue >= -135.0 && zValue < -45.0)
            return Direction.RIGHT;
        else
            return Direction.BACKWARD;
    }
    
    public Tilt getTilt() {
        double xMag = Math.abs(gyro.getX());
        double yMag = Math.abs(gyro.getY());
        double greatestTilt = (xMag > yMag) ? xMag : yMag;
        
        if (greatestTilt >= 0.0 && greatestTilt < 5.0)
            return Tilt.NOMINAL;
        else if (greatestTilt >= 5.0 && greatestTilt < 15.0)
            return Tilt.INCLINED;
        else if (greatestTilt >= 15.0 && greatestTilt < 30.0)
            return Tilt.NEAR_FATAL;
        else
            return Tilt.FATAL;
    }
    
}
*/