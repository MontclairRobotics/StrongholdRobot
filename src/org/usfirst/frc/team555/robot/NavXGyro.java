package org.usfirst.frc.team555.robot;
import com.kauailabs.navx.frc.AHRS;

public class NavXGyro {
    
    // AHRS reference from wherever NavXGyro is initialized 
    private AHRS ahrs;
    
    public NavXGyro(AHRS ahrs) {
        this.ahrs = ahrs;
         // Resets gyro readings to 0 (starting orientation)
        ahrs.reset();
    }
    
    /* 
    NOTE: - getX(), getY(), and getZ() are measured in degrees
          - Range: [-180°, 180°) 
          - ahrs.getPitch(), ahrs.getRoll(), and ahrs.getYaw() return type float
    */
    
    public float getPitch() {
        return ahrs.getPitch();
    }
    
    public float getRoll() {
        return ahrs.getRoll(); // Ignore documentation; there is a typo
    }
    
    public float getYaw() {
        return ahrs.getYaw();
    }
    
}
