package org.usfirst.frc.team555.robot;
import com.kauailabs.navx.frc.AHRS;

public class NavXAccelerometer {
    
    private AHRS ahrs; // References the NavX computer
    
    public NavXAccelerometer(AHRS ahrs) {
        this.ahrs = ahrs;
    }
    
    // Acceleration along x-axis, y-axis, and z-axis
    
    public double getAccelX() {
        return ahrs.getWorldLinearAccelX() * 9.807; // in m/s^2
    }
    
    public double getAccelY() {
        return ahrs.getWorldLinearAccelY() * 9.807; // in m/s^2
    }
    
    public double getAccelZ() {
        return ahrs.getWorldLinearAccelZ() * 9.807; // in m/s^2
    }
    
    // Velocity along x-axis, y-axis, and z-axis
    
    public double getVelocX() {
        return ahrs.getVelocityX(); // in m/s
    }
    
    public double getVelocY() {
        return ahrs.getVelocityY(); // in m/s
    }
    
    public double getVelocZ() {
        return ahrs.getVelocityZ(); // in m/s
    }
    
}
