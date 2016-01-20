package org.usfirst.frc.team555.robot;
import com.kauailabs.navx.frc.AHRS;

public class NavXAccelerometer {
    
    private AHRS ahrs;
    
    public NavXAccelerometer(AHRS ahrs) {
        this.ahrs = ahrs;
    }
    
    public getX() {
        return (double)ahrs.getWorldLinearAccelX() / 9.8;
    }
    
    public getY() {
        return (double)ahrs.getWorldLinearAccelX() / 9.8;
    }
    
    public getZ() {
        return (double)ahrs.getWorldLinearAccelX() / 9.8;
    }
    
}
