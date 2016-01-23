package org.usfirst.frc.team555.robot;
import com.kauailabs.navx.frc.AHRS;

public class NavXGyro {
    
    // AHRS reference from wherever NavXGyro is initialized 
    private AHRS ahrs;
    
    public NavXGyro(AHRS ahrs) {
        this.ahrs = ahrs;
        ahrs.reset();// Resets gyro readings to 0 (starting orientation)
    }
    
    // Gyroscopic Readings along x-axis, y-axis, z-axis
    
    public double getX() {
        return ahrs.getPitch(); // In degrees [180°, 180°)
    }
    
    public double getY() {
        return ahrs.getRoll(); // In degrees [180°, 180°)
    }
    
    public double getZ() {
        return ahrs.getYaw(); // In degrees [180°, 180°)
    }
    
}
