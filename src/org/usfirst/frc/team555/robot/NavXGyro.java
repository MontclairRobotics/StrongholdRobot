package org.usfirst.frc.team555.robot;
import com.kauailabs.navx.frc.AHRS;

public class NavxGyro {
    
    // AHRS reference from wherever NavXGyro is initialized 
    private AHRS ahrs;
    
    private public NavXGyro(AHRS ahrs) {
        this.ahrs = ahrs;
         // Resets gyro readings to 0 (starting orientation)
        ahrs.reset();
    }
    
    /* 
    NOTE: - getX(), getY(), and getZ() are measured in degrees
          - Range: [-180°, 180°) 
          - ahrs.getPitch(), ahrs.getRoll(), and ahrs.getYaw() return type float
    */
    
    public double getX() {
        return (double)ahrs.getPitch();
    }
    
    public double getY() {
        return (double)ahrs.getRoll(); // Ignore documentation; there is a typo
    }
    
    public double getZ() {
        return (double)ahrs.getYaw();
    }
    
}
