package org.usfirst.frc.team555.robot;

public class UShooter extends Shooter {
	
	ShooterUltrasonic ultrasonic;
	
	public UShooter() {
		super();
		
		ultrasonic = new ShooterUltrasonic();
	}
	
	public void update() {
		super.update();
		
		if (distanceIsNominal())
			Robot.dashboard.putString("Distance", "NOMINAL: " + ultrasonic.distanceFT + "ft");
		else
			Robot.dashboard.putString("Distance", "OUT OF RANGE: " + ultrasonic.distanceFT + "ft");
	}

    public boolean distanceIsNominal() {
        return ultrasonic.distanceFT > 5 && ultrasonic.distanceFT < 21;
    }
    
}
