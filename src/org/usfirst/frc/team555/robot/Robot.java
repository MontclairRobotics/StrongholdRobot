package org.usfirst.frc.team555.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends IterativeRobot {
	
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    
    DriveTrain driveTrain;
    ManualShooter shooter;
    AutoShooter autoShooter;
    
    AHRS ahrs;
    NavXAccelerometer accel;
    public static NavXGyro gyro;
    //PitchCorrector corrector;
    
    public static SmartDashboard dashboard;
    Thread dashboardThread;
    
    boolean[] lastValveButton;

    
    public void robotInit() {
    	dashboard = new SmartDashboard();
    	//dashboardThread = new Thread(dashboard);
    	//dashboardThread.start();
    	
    	chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        dashboard.putData("Auto choices", chooser);     
        dashboard.putNumber("PID-P", DriveMotor.PID_P);
        dashboard.putNumber("PID-I", DriveMotor.PID_I);
        dashboard.putNumber("PID-D", DriveMotor.PID_D);
        
        driveTrain = new DriveTrain();
        shooter = new ManualShooter(driveTrain);
        autoShooter=new AutoShooter(shooter);
        
        ahrs = new AHRS(SPI.Port.kMXP);
        accel = new NavXAccelerometer(ahrs);
        gyro = new NavXGyro(ahrs);
        //corrector = new PitchCorrector(gyro, driveTrain);
    }

    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = dashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		driveTrain.setDistance(500, 10, 0);//PUT IN REAL VALUES
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }
    
    /**
     * This function is called periodically during operator control
     */
    
    public void teleopInit() {
    	DriveMotor.PID_P = dashboard.getNumber("PID-P");
        DriveMotor.PID_I = dashboard.getNumber("PID-I");
        DriveMotor.PID_D = dashboard.getNumber("PID-D");
    }
    
    public void teleopPeriodic() {
    	driveTrain.setSpeedXY(Control.getX(Control.DRIVE_STICK), -Control.getY(Control.DRIVE_STICK));
    	driveTrain.setLock(Control.getButton(Control.DRIVE_STICK,Control.LOCK_BUTTON));
        autoShooter.setActive(Control.getButton(Control.DRIVE_STICK,Control.AUTOTARGET));
        shooter.setLift(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_UP),
        		Control.getButton(Control.SHOOT_STICK,Control.SHOOT_DOWN));
        shooter.setJoystick(Control.getX(Control.SHOOT_STICK),-Control.getY(Control.SHOOT_STICK));
        
        update();
    }
    
    public void update() {
    	driveTrain.update();
    	shooter.update();
    	autoShooter.update();
    	dashboard.putNumber("gyro-angle", gyro.getYaw());
    	dashboard.putNumber("accel-x", accel.getAccelX());
    	dashboard.putNumber("accel-y", accel.getAccelY());
    	dashboard.putNumber("accel-z", accel.getAccelZ());
    	dashboard.putNumber("accel-yaw", gyro.getYaw());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
