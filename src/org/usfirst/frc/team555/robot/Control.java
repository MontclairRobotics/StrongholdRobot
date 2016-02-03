package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Control {
	
	public static final int DRIVE_STICK = 0;
	public static final int SHOOT_STICK = 1;
	public static final int[] VALVES={0,1}; //TODO: Ask Jack
	public static final int[] SHOOT_BUTTONS = {5, 3};
	public static final int AUTOTARGET=4;//TODO
	public static final int SHOOT_TRIGGER=6;//TODO
	public static final int HALVING_BUTTON = 2;
	
	private static Joystick[] sticks = {
			new Joystick(DRIVE_STICK),
			new Joystick(SHOOT_STICK)
	};
	
	
	public static double getX(int joystick) {
		Robot.dashboard.putNumber("X:" + joystick, sticks[joystick].getX());
		return sticks[joystick].getX();
	}
	
	public static double getY(int joystick) {
		Robot.dashboard.putNumber("Y:" + joystick, sticks[joystick].getY());
		return sticks[joystick].getY();
	}
	
	public static double getZ(int joystick) {
		//Robot.dashboard.putNumber("Z:" + joystick, sticks[joystick].getYaw());
		return sticks[joystick].getZ();
	}
	
	public static double getMagnitude(int joystick) {
		return sticks[joystick].getMagnitude();
	}
	
	public static double getDegrees(int joystick) {
		return sticks[joystick].getDirectionDegrees();
	}
	
	public static boolean getButton(int joystick, int button)
	{
		return sticks[joystick].getRawButton(button);
	}
	
	static boolean halvingButtonPressed() {
		return getButton(DRIVE_STICK, HALVING_BUTTON);
	}
	
}
