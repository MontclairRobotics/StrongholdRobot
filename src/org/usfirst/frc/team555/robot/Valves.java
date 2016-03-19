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
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
		halfOn();
	}
	
	public void lower()
	{
		for(Solenoid s : LiftValves) {
			s.set(false);
		}
		halfOff();
	}
	
	//TODO: Fill these in
	public void raiseOne(){
		
	}
	
	public void lowerOne(){
		
	}
	
	public void halfOn()
	{
		for(Solenoid s:HalfValves)
		{
			s.set(true);
		}
	}
	public void halfOff()
	{
		for(Solenoid s:HalfValves)
		{
			s.set(false);
		}
	}
	
	public void shootOut()
	{
		for(Solenoid s : ShooterValves) {
			s.set(true);
		}
	}
	
	public void shootIn()
	{
		for(Solenoid s : ShooterValves) {
			s.set(false);
		}
	}
}
