package org.usfirst.frc.team555.robot;

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
        Robot.dashboard.putString("Direction", getDirection().toString() + 
            "\n\tZ: " + String.valueOf(gyro.getYaw()) + "°");
        Robot.dashboard.putString("Tilt", getTilt().toString() + 
            "\n\tX: " + String.valueOf(gyro.getPitch()) + "°, Y: " + String.valueOf(gyro.getRoll()) + "°");
    }
    
    public Direction getDirection() {
        double zValue = gyro.getYaw();
        
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
        double xMag = Math.abs(gyro.getPitch());
        double yMag = Math.abs(gyro.getRoll());
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
