package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NetworkTableTestRobot 
{
	NetworkTable table;
	
	public void setup()
	{
		table=NetworkTable.getTable("data");
	}
	
	public void update()
	{
		int x=(int)(Math.random()*5+1);
		int y=(int)(Math.random()*5+1);
		
		table.putNumber("X",x);
		table.putNumber("Y",y);
	}
}