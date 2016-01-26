package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PitchCorrector {
	
	public static final double CORRECTION_FACTOR = 0.05; // 5%
	public static final double TIP_THRESHOLD = 20.0; // 20°
	
	NavXGyro gyro;
	DriveTrain train;
	
	int consecutiveCorrections;
	
	public PitchCorrector(NavXGyro gyro, DriveTrain train) {
		this.gyro = gyro;
		this.train = train;
		consecutiveCorrections = 0;
	}
	
	public void update() {
		if (gyro.getX() < -TIP_THRESHOLD) {
			correctNegativePitch();
			consecutiveCorrections++;
		} else if (gyro.getX() > TIP_THRESHOLD) {
			correctPositivePitch();
			consecutiveCorrections++;
		} else {
			consecutiveCorrections = 0;
			SmartDashboard.putString("Pitch Corrections", consecutiveCorrections + " consecutive corrections made");
		}
	}
	
	public void correctNegativePitch() {
		double factor = 1.0 + CORRECTION_FACTOR;
		train.leftSpd *= factor;
	}
	
	public void correctPositivePitch() {
		double factor = 1.0 - CORRECTION_FACTOR;
		train.rightSpd *= factor;
	}
	
}
