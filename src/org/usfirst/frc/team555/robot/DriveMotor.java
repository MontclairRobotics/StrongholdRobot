package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;

public class DriveMotor {
	
	private int motorPort, encoderPort1, encoderPort2;
	private double speed;
	
	private Encoder encoder;
	private CANTalon motor;
	private PIDController controller;
	
	private boolean encoders;
	
	private static boolean shutdown=false;
	
	public static final int ROT_TO_DEGREES = 360;
	public static double PID_P = 0.1, PID_I = 0.001, PID_D = 0.0;
	public static final double SCALE_FACTOR = 1;
	
	public DriveMotor(int id, boolean encoders)
	{
		this(Map.MOTOR_PORTS[id], encoders, 's');
	}
	
	public DriveMotor(int id) {
		this(Map.MOTOR_PORTS[id], true, 'd');
	}
	
	public DriveMotor(int[] ports, boolean encoders, char type) {
		this.encoders = encoders;
		//int[][] ports = type == 'd' ? Map.MOTOR_PORTS : Map.SHOOTER_PORTS;
		motorPort = ports[0];
		//TODO: What!?
		motor = new CANTalon(motorPort);
		if(encoders) {
			encoderPort1 = ports[1];
			encoderPort2 = ports[2];
			
			encoder = new Encoder(encoderPort1, encoderPort2);
			controller = new PIDController(PID_P,PID_I,PID_D, encoder, motor);
			controller.enable();
		}
		motor.setControlMode(TalonControlMode.PercentVbus.value);
		motor.reset();
		motor.enable();
		motor.enableControl();
	}
	
	public void setSpeed(double spd)
	{
		speed = encoders ? spd * ROT_TO_DEGREES : spd*SCALE_FACTOR; //TODO: Control scale constant
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
			motor.disableControl();
			return;
		}
		if(encoders) {
			controller.setSetpoint(speed);
		} else {
			motor.set(speed);
			Robot.dashboard.putNumber("Speed-" + motorPort, speed);
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
	
	public double getDistance()
	{
		if(!encoders)return 0;
		return encoder.getDistance();
	}
	
	public void resetDistance()
	{
		if(!encoders)return;
		encoder.reset();
	}
	
}
