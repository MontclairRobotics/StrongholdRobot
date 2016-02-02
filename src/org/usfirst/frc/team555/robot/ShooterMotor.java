package org.usfirst.frc.team555.robot;

public class ShooterMotor extends DriveMotor {
	
	public ShooterMotor(int id, boolean encoders) {
		this(Map.SHOOTER_MOTORS[id], encoders, 's');
	}
	
	public ShooterMotor(int id) {
		this(Map.SHOOTER_MOTORS[id], false, 's');
	}
	
	public ShooterMotor(int[] id, boolean encoders, char type) {
		super(id, encoders, type);
	}
	
}
