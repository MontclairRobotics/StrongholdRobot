package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveModule {
	
	private int motorPort, encoderPort1, encoderPort2;
	private double speed;
	
	private Encoder encoder;
	private TalonSRX motor;
	private PIDController controller;
	SmartDashboard display;
	
	private static boolean shutdown;
	
	public static final int ROT_TO_DEGREES = 360;
	public static double PID_P = 0.1, PID_I = 0.001, PID_D = 0.0;
	
	public DriveModule(int id)
	{
		switch(id)
		{
		case 0:
			motorPort=0;
			encoderPort1=1;
			encoderPort2=2;
			break;
		case 1:
			motorPort=3;
			encoderPort1=4;
			encoderPort2=5;
			break;
			
		//TODO: Fill in rest
			
		default:
			shutdown=true;
			break;
		}
		
		
		//begin
		encoder = new Encoder(encoderPort1, encoderPort2);
		motor = new TalonSRX(motorPort);
		controller = new PIDController(PID_P,PID_I,PID_D, encoder, motor);
		controller.enable();
	}
	
	public void setSpeed(double spd)
	{
		speed = spd * ROT_TO_DEGREES;
	}
	
	public void update()
	{
		if(shutdown)
		{
			controller.disable();
			return;
		}
		controller.setPID(PID_P, PID_I, PID_D);
		controller.setSetpoint(speed);
	}
	
	public static void shutdown()
	{
		shutdown = true;
	}
}
