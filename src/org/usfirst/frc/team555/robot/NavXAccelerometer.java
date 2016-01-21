package org.usfirst.frc.team555.robot;
import com.kauailabs.navx.frc.AHRS;

public class NavXAccelerometer {
    
    private AHRS ahrs; // References the NavX computer
    
    public NavXAccelerometer(AHRS ahrs) {
        this.ahrs = ahrs;
    }
    
    /*
    NOTE: - acceleration functions are measured in "m/s^2"
          - ahrs functions are of type float
    */
    
    public getAccelX() {
        return (double)ahrs.getWorldLinearAccelX() / 9.806;
    }
    
    public getAccelY() {
        return (double)ahrs.getWorldLinearAccelX() / 9.806;
    }
    
    public getAccelZ() {
        return (double)ahrs.getWorldLinearAccelX() / 9.806;
    }
    
    /*
    NOTE: - velocity functions are measured in "m/s"
          - ahrs functions are of type float
    */
    
    public getVelocX() {
        return (double)ahrs.getVelocityX();
    }
    
    public getVelocY() {
        return (double)ahrs.getVelocityY();
    }
    
    public getVelocZ() {
        return (double)ahrs.getVelocityZ();
    }
    
}
