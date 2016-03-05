package org.usfirst.frc.team555.robot;

import com.kauailabs.navx.frc.AHRS;

public class NavXAccelerometer {
    
    private static final double GRAVITATIONAL_CONSTANT = 9.807;
    
    private AHRS ahrs; // References the NavX computer
    
    public NavXAccelerometer(AHRS ahrs) {
        this.ahrs = ahrs;
    }
    
    // Acceleration along x-axis, y-axis, and z-axis
    
    public double getAccelX() {
        return ahrs.getWorldLinearAccelX() * GRAVITATIONAL_CONSTANT; // in m/s^2
    }
    
    public double getAccelY() {
        return ahrs.getWorldLinearAccelY() * GRAVITATIONAL_CONSTANT; // in m/s^2
    }
    
    public double getAccelZ() {
        return ahrs.getWorldLinearAccelZ() * GRAVITATIONAL_CONSTANT; // in m/s^2
    }
    
    // Overall Speed
    
    public double getSpeed() {
        return Math.sqrt(Math.pow(getVelocX(), 2.0) + Math.pow(getVelocY(), 2.0) + Math.pow(getVelocZ(), 2.0)); // In m/s
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
