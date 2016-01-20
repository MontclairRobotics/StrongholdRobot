package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;

public class HI {
	
	private static Joystick[] sticks;
	
	public static final int DRIVE_STICK = 0;
	public static final int SHOOT_STICK = 1;
	
	public static HumanInterface()
	{
		sticks = new Joystick[2];
		sticks[0] = new Joystick(0);
		sticks[1] = new Joystick(1);
	}
	
	public static double getX(int joystick) {
		return sticks[joystick].getX();
	}
	
	public static double getY(int joystick) {
		return sticks[joystick].getY();
	}
	
	public static double getZ(int joystick) {
		return sticks[joystick].getZ();
	}
}