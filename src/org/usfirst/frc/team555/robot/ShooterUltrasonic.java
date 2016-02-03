/*package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Ultrasonic;

public class ShooterUltrasonic extends Ultrasonic {
    
    double distanceIN = 0; // Distance to tower in millimeters
    double distanceCM = 0; // Distance to tower in centimeters
    
    NavXGyroMonitor monitor;
    
    public ShooterUltrasonic(NavXGyroMonitor monitor) {
        super(0, 0); // TODO: - Replace 0 with valid port values
        
        this.monitor = monitor;
        
        ping(); // Get an initial readout
    }
    
    public void display() {
        if (isNominal())
            Robot.dashboard.putString("Shooter", "Distance is NOMINAL\n\t" + distanceIN + "in (" + distanceCM + "cm)");
        else
            Robot.dashboard.putString("Shooter", "Distance is NOT nominal\n\t" + distanceIN + "in (" + distanceCM + "cm)");
    }
    
    public void update() {
        ping(); // Retrieve distance
        
        distanceIN = getRangeInches();
        distanceCM = getRangeMM() / 10;
    }
    
    public boolean isNominal() {
        return (distanceIN > 60.0 && distanceIN < 120.0) &&
            monitor.getDirection() == NavXGyroMonitor.Direction.FORWARD;
    }
    
}
*/