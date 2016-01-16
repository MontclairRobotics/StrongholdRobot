package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveMotor {
	
	private int motorPort, encoderPort1, encoderPort2;
	private double speed;
	
	private Encoder encoder;
	private TalonSRX motor;
	private PIDController controller;
	SmartDashboard display;
	
	private boolean encoders;
	
	private static boolean shutdown;
	
	public static final int ROT_TO_DEGREES = 360;
	public static double PID_P = 0.1, PID_I = 0.001, PID_D = 0.0;
	
	public DriveMotor(int id, boolean encoders)
	{
		this(id, encoders, 's');
	}
	
	public DriveMotor(int id) {
		this(id, true, 'd');
	}
	
	public DriveMotor(int id, boolean encoders, char type) {
		this.encoders = encoders;
		int[][] ports = type == 'd' ? Map.MOTOR_PORTS : Map.SHOOTER_PORTS;
		motorPort = ports[id][0];
		motor = new TalonSRX(motorPort);
		if(encoders) {
			encoderPort1 = ports[id][1];
			encoderPort1 = ports[id][2];
			
			encoder = new Encoder(encoderPort1, encoderPort2);
			controller = new PIDController(PID_P,PID_I,PID_D, encoder, motor);
			controller.enable();
		}
	}
	
	public void setSpeed(double spd)
	{
		speed = encoders ? spd * ROT_TO_DEGREES : spd;
	}
	
	public void update()
	{
		if(shutdown)
		{
			if(encoders) {
				controller.disable();
			} else {
				speed = 0;
			}
			motor.set(0);
			return;
		}
		if(encoders) {
			controller.setPID(PID_P, PID_I, PID_D);
			controller.setSetpoint(speed);
		} else {
			motor.set(speed);
		}
	}
	
	public boolean isInverted() {
		return motor.getInverted();
	}
	
	public void setInverted(boolean invert) {
		motor.setInverted(invert);
	}
	
	public void toggleInvert() {
		motor.setInverted(!motor.getInverted());
	}
	
	public static void shutdown()
	{
		shutdown = true;
	}
	
}
