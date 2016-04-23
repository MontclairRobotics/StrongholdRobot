package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Valves {
	
	private Solenoid[] LiftValves;
	private Solenoid[] ShooterValves;
	private Solenoid[] HalfValves;
	
	public Valves()
	{
		LiftValves = new Solenoid[Map.LIFT_SOLENOID_PORTS.length];
		ShooterValves = new Solenoid[Map.SHOOTER_SOLENOID_PORTS.length];
		HalfValves = new Solenoid[Map.LIFT_HALF_PORTS.length];
        for(int i = 0; i < Map.LIFT_SOLENOID_PORTS.length; i++)
        {
        	LiftValves[i] = new Solenoid(Map.LIFT_SOLENOID_PORTS[i]);
        }
        
        for(int i = 0; i < Map.SHOOTER_SOLENOID_PORTS.length; i++)
        {
        	
        	ShooterValves[i] = new Solenoid(Map.SHOOTER_SOLENOID_PORTS[i]);
        }
        
        for(int i = 0; i < Map.LIFT_HALF_PORTS.length; i++)
        {
        	HalfValves[i] = new Solenoid(Map.LIFT_HALF_PORTS[i]);
        }
	}
	
	public void raise()
	{
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
		halfOn();
	}
	
	public void raiseArm() {
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(false);
		}
	}
	
	public void lower()
	{
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
		halfOn();
	}
	
	public void raiseOne(){
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
	}
	
	public void lowerOne(){
		resetShooterPush();
		for(Solenoid s : LiftValves) {
			s.set(false);
		}
	}
	
	public void halfOn()
	{
		resetShooterPush();
		for(Solenoid s : HalfValves)
		{
			s.set(false);
		}
	}
	public void halfOff()
	{
		resetShooterPush();
		for(Solenoid s : HalfValves)
		{
			s.set(true);
		}
	}
	
	public void shootOut()
	{
		for(Solenoid s : ShooterValves) {
			//Robot.dashboard.putString("Shoot-status", "out");
			//Robot.dashboard.putData("solenoid", s);
			s.set(true);
		}
	}
	
	public void shootIn()
	{
		for(Solenoid s : ShooterValves) {
			//Robot.dashboard.putString("Shoot-status", "in");
			s.set(false);
		}
	}
	
	public void resetShooterPush() {
		for(Solenoid s : ShooterValves)
        {
        	s.set(false);
        }
	}
	
	public void lowerArm() {
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
	}
	
	public boolean isHalfExtended() {
		return !HalfValves[0].get();
	}
	
	public boolean isArmUp() {
		return !LiftValves[0].get();
	}
	
}
