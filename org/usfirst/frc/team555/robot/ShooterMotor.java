package org.usfirst.frc.team555.robot;

public class ShooterMotor extends DriveMotor {
	
	public ShooterMotor(int id, boolean encoders) {
		super(Map.SHOOTER_MOTORS[id], encoders, 's');
	}
	
	public ShooterMotor(int id) {
		super(Map.SHOOTER_MOTORS[id], false, 's');
	}
	
	public ShooterMotor(int id, boolean encoders, char type) {
		super(Map.SHOOTER_MOTORS[id], encoders, type);
	}
	
}
