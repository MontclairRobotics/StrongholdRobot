package org.usfirst.frc.team555.robot;

public class NavxGyro {
    
    private AHRS ahrs;
    
    private public NavXGyro(AHRS ahrs) {
        this.ahrs = ahrs;
        ahrs.reset(); // Resets gyro readings to 0
    }
    
    public double getX() {
        return (double)ahrs.getPitch();
    }
    
    public double getY() {
        return (double)ahrs.getRool();
    }
    
    public double getZ() {
        return (double)ahrs.getYaw();
    }
    
}
