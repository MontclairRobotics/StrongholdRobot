package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Ultrasonic;

public class ShooterUltrasonic extends Ultrasonic {
    
    double distanceIN = 0; // Distance to tower in inches
    double distanceCM = 0; // Distance to tower in centimeters
    
    public ShooterUltrasonic() {
        super(0, 0); // TODO: - Replace 0 with valid port values
        
        ping(); // Get an initial readout
    }
    
    public void update() {
        ping(); // Retrieve distance
        
        distanceIN = getRangeInches();
        distanceCM = getRangeMM() / 10;
    }
    
}