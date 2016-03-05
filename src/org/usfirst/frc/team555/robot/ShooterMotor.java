package org.usfirst.frc.team555.robot;

public class ShooterMotor extends DriveMotor {

	public static char defaultType='t';
	
	public ShooterMotor(int id) {
		super(Map.SHOOTER_MOTORS[id],false,defaultType);
	}
	public ShooterMotor(int id, boolean encoders) {
		super(Map.SHOOTER_MOTORS[id], encoders,defaultType);
	}
	
	
	public ShooterMotor(int id, boolean encoders, char type) {
		super(Map.SHOOTER_MOTORS[id], encoders, type);
	}
	public ShooterMotor(int[] ports,boolean encoders,char type)
	{
		super(ports,encoders,type);
	}
	
}
