package org.usfirst.frc.team555.robot;
import com.kauailabs.navx.frc.AHRS;

public class GyroMonitor {
    
    public enum Direction {
        FORWARD, LEFT, RIGHT, BACKWARD;
    }
    
    public enum Tilt {
        NOMINAL, INCLINED, NEAR_FATAL, FATAL;
    }
    
    NavXGyro gyro; // Reference to NavX gyroscope
    
    public GyroMonitor(NavXGyro gyro) {
        this.gyro = gyro;
    }
    
    public void display() {
        SmartDashboard.putString("Direction", getDirection().toString() + 
            "\nZ-axis: " + String.valueOf(gyro.getZ()) + "°");
        SmartDashboard.putString("Tilt", getTilt().toString() + 
            "\nX-axis: " + String.valueOf(gyro.getX()) + "°, Y-axis: " + String.valueOf(gyro.getY()) + "°");
    }
    
    public Direction getDirection() {
        double zValue = gyro.getZ();
        
        if (zValue >= -45 && zValue < 45)
            direction = Direction.FORWARD;
        else if (zValue >= 45 && zValue < 135)
            direction = Direction.LEFT;
        else if (zValue >= -135 && zValue < -45)
            direction = Direction.RIGHT;
        else
            direction = Direction.BACKWARD;
    }
    
    public Tilt getTilt() {
        double xMag = Math.abs(gyro.getX());
        double yMag = Math.abs(gyro.getY());
        double greatestTilt = (xMag > yMag) ? xMag : yMag;
        
        if (greatestTilt < 5) // This represents whatever difference might be caused by regular jostling 
            tilt = Tilt.NOMINAL;
        else if (greatestTilt < 20)
            tilt = Tile.INCLINED;
        else if (greatistTilt < 30) // TODO: - Replace 30° with whatever the tipping point of the robot is
            tilt = Tilt.NEAR_FATAL;
        else
            tilt = Tilt.FATAL;
    }
    
}
