package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Valves {
	
	private Solenoid[] valves;
	
	public Valves()
	{
		valves=new Solenoid[2];
        for(int i=0;i<Map.SOLINOID_PORTS.length;i++)
        {
        	valves[i] = new Solenoid(Map.SOLINOID_PORTS[i]);
        }
	}
	
	public void raise()
	{
		
	}
	
	public void lower()
	{
		
	}
	
	public void shootOut()
	{
		
	}
	
	public void shootIn()
	{
		
	}
}
