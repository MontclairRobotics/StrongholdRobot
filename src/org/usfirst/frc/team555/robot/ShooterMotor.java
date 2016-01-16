package org.usfirst.frc.team555.robot;

public class ShooterMotor extends DriveMotor {
	
	public ShooterMotor(int id, boolean encoders) {
		super(id, encoders, 's');
	}
	
	public ShooterMotor(int id) {
		super(id, false, 's');
	}
	
	public ShooterMotor(int id, boolean encoders, char type) {
		super(id, encoders, type);
	}
	
}
