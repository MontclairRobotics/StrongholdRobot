package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CourseLockPIDSource implements PIDSource {
	double target=.0;
	PIDSourceType mPIDSourceType = PIDSourceType.kDisplacement;

	public CourseLockPIDSource(){
		Robot.dashboard.putString("PIDSource", "constructed");
	}
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		mPIDSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return mPIDSourceType;
	}
	public void setTarget(){
		Robot.gyro.zeroYaw();
		target = Robot.gyro.getYaw();
	}
	public void setTarget(double newTarget){
		target = newTarget;
	}
	public void setRelativeTarget(double difference){
		target = Robot.gyro.getYaw()+difference;
	}

	@Override
	public double pidGet() {
		Robot.dashboard.putString("pidGet", "started");
		double angle=Robot.gyro.getYaw()-target;
		Robot.dashboard.putNumber("relativeAngle", angle);
		return angle;
	}

}
