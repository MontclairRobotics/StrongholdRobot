package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Valves {
	
	private Solenoid[] LiftValves;
	private Solenoid[] ShooterValves;
	
	public Valves()
	{
		LiftValves=new Solenoid[2];
        for(int i=0;i<Map.LIFT_SOLENOID_PORTS.length;i++)
        {
        	LiftValves[i] = new Solenoid(Map.LIFT_SOLENOID_PORTS[i]);
        }
        
        for(int i=0;i<Map.SHOOTER_SOLENOID_PORTS.length;i++)
        {
        //	ShooterValves[i] = new Solenoid(Map.SHOOTER_SOLENOID_PORTS[i]);
        }
        
	}
	
	public void raise()
	{
		for(Solenoid s : LiftValves) {
			s.set(true);
		}
	}
	
	public void lower()
	{
		for(Solenoid s : LiftValves) {
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
