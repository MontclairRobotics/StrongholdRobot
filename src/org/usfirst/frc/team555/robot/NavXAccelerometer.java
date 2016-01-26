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
    
    public double getAccelX() {
        return ahrs.getWorldLinearAccelX();
    }
    
    public double getAccelY() {
        return ahrs.getWorldLinearAccelX();
    }
    
    public double getAccelZ() {
        return ahrs.getWorldLinearAccelX();
    }
    
    /*
    NOTE: - velocity functions are measured in "m/s"
          - ahrs functions are of type float
    */
    
    public double getVelocX() {
        return ahrs.getVelocityX();
    }
    
    public double getVelocY() {
        return ahrs.getVelocityY();
    }
    
    public double getVelocZ() {
        return ahrs.getVelocityZ();
    }
    
}
