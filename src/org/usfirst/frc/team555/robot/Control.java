package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Control {
	
	public static final int DRIVE_STICK = 0;
	public static final int SHOOT_STICK = 1;
	public static final int[] VALVES={0,1}; //TODO: Ask Jack
	public static final int[] SHOOT_BUTTONS = {5, 3};
	
	private static Joystick[] sticks = {
			new Joystick(DRIVE_STICK),
			new Joystick(SHOOT_STICK)
	};
	
	
	public static double getX(int joystick) {
		return sticks[joystick].getX();
	}
	
	public static double getY(int joystick) {
		return sticks[joystick].getY();
	}
	
	public static double getZ(int joystick) {
		return sticks[joystick].getZ();
	}
	
	public static boolean getButton(int joystick, int button)
	{
		return sticks[joystick].getRawButton(button);
	}
}
