package org.usfirst.frc.team555.robot;

public class ShooterUltrasonic extends Ultrasonic {
    
    public final double[] NOMINAL_RANGE_IN = {60.0, 120.0};
    public final double[] NOMINAL_RANGE_CM = {130.0, 250.0};
    
    double distanceIN = 0; // Distance to tower in millimeters
    double distanceCM = 0; // Distance to tower in centimeters
    
    NavXGyroMonitor monitor;
    
    public ShooterUltrasonic(NavXGyroMonitor monitor) {
        this.monitor = monitor;
        
        super(0, 0); // TODO: - Replace 0 with valid port values
        ping(); // Get an initial readout
    }
    
    public void display() {
        if isNominal()
            SmartDashboard.putString("Shooter", "Distance is NOMINAL
                \n\t" + distanceIN.toString() + "in (" + distanceCM.toString() + "cm)");
        else
            SmartDashboard.putString("Shooter", "Distance is NOT nominal
                \n\t" + distanceIN.toString() + "in (" + distanceCM.toString() + "cm)");
    }
    
    public void update() {
        ping(); // Retrieve distance
        
        distanceIN = getRangeInches();
        distanceCM = getRangeMM() / 10;
    }
    
    public boolean isNominal() {
        return (distanceIN > NOMINAL_RANGE[0] && distanceIN < NOMINAL_RANGE[1]) &&
            monitor.getDirection == Direction.FORWARD;
    }
    
}
