
package org.usfirst.frc.team555.robot;
import com.kauailabs.navx.frc.AHRS;

public class NavXGyro {
    
    // AHRS reference from wherever NavXGyro is initialized 
    private AHRS ahrs;
    
    public NavXGyro(AHRS ahrs) {
        this.ahrs = ahrs;
        ahrs.reset();// Resets gyro readings to 0 (starting orientation)
    }
    
    // Gyroscopic readings along x-axis, y-axis, z-axis
    
    public float getPitch() {
        return ahrs.getPitch();
    }
    
    public float getRoll() {
        return ahrs.getRoll(); // Ignore documentation; there is a typo
    }
    
    public float getYaw() {
        return ahrs.getYaw();
    }
    
    public void zeroYaw()
    {
        ahrs.zeroYaw();
    }
    
}
