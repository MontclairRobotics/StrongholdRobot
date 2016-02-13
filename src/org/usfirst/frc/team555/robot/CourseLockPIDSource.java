package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CourseLockPIDSource implements PIDSource {
	double target;

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}
	public void setTarget(){
		target = Robot.gyro.getYaw();
	}

	@Override
	public double pidGet() {
		double angle=Robot.gyro.getYaw()+target;
		while (angle<-180.0 || angle>180.0){
			if(angle >= 180.0){
				angle-=360.0;
			}
			else if(angle <-180.0){
				angle+=360.0; 
			}
		}
		return angle;
	}

}
