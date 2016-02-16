package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.PIDOutput;

public class CourseLockPIDOut implements PIDOutput {
	
	double output = 0.2;
	public void pidWrite(double out){
		this.output = out;
		Robot.dashboard.putNumber("pidWrite", out);
	}
	public double getOutput(){
		Robot.dashboard.putNumber("getOutput", output);
		return output;
	}

}
