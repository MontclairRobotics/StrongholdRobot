package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;

public class HumanInterface {
	
	private Joystick[] sticks;
	
	public static final int DRIVE_STICK = 0;
	public static final int SHOOT_STICK = 1;
	
	public HumanInterface()
	{
		sticks = new Joystick[2];
		sticks[0] = new Joystick(0);
		sticks[1] = new Joystick(1);
	}
	
	public double getX(int joystick) {
		return sticks[joystick].getX();
	}
	
	public double getY(int joystick) {
		return sticks[joystick].getY();
	}
	
	public double getZ(int joystick) {
		return sticks[joystick].getZ();
	}
}
