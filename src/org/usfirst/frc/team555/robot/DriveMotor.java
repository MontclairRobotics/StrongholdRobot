package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveMotor {
	
	private int motorPort, encoderPort1, encoderPort2;
	private double speed;
	
	private Encoder encoder;
	private SpeedController motor;
	private PIDController controller;
	
	private boolean encoders;
	
	private static boolean shutdown=false;
	private static boolean SRX = false; //TRUE FOR TALONSRX, FALSE FOR VICTORSP
	
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
		if(SRX) {
			motor = new CANTalon(motorPort);
		} else {
			motor = new VictorSP(motorPort);
		}
		
		if(encoders) {
			encoderPort1 = ports[1];
			encoderPort2 = ports[2];
			
			encoder = new Encoder(encoderPort1, encoderPort2);
			controller = new PIDController(PID_P,PID_I,PID_D, encoder, motor);
			controller.enable();
		}
		if(motor instanceof CANTalon) {
			CANTalon talon = (CANTalon)motor;
			talon.setControlMode(TalonControlMode.PercentVbus.value);
			talon.reset();
			talon.enable();
			talon.enableControl();
		}
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
			if(motor instanceof CANTalon) ((CANTalon)motor).disableControl();
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
	
	public static void shutdown() {
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
	
	/*public SpeedController getMotor() {
		return motor;
	}*/
	
}
