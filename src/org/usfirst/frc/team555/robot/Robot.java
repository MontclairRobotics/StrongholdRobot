
package org.usfirst.frc.team555.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    
    DriveTrain driveTrain;
    Shooter shooter;
    
    AHRS ahrs;
    NavXAccelerometer accel;
    NavXGyro gyro;
    
    boolean[] lastValveButton;

    
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);     
        SmartDashboard.putNumber("PID-P", DriveMotor.PID_P);
        SmartDashboard.putNumber("PID-I", DriveMotor.PID_I);
        SmartDashboard.putNumber("PID-D", DriveMotor.PID_D);
        
        driveTrain = new DriveTrain();
        shooter = new Shooter();
        
        ahrs = new AHRS(SPI.Port.kMXP);
        accel = new NavXAccelerometer(ahrs);
        gyro = new NavXGyro(ahrs);
    }

    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
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
    	DriveMotor.PID_P = SmartDashboard.getNumber("PID-P");
        DriveMotor.PID_I = SmartDashboard.getNumber("PID-I");
        DriveMotor.PID_D = SmartDashboard.getNumber("PID-D");
    }
    
    public void teleopPeriodic() {
        driveTrain.setSpeedArcade(Control.getY(Control.DRIVE_STICK), Control.getZ(Control.DRIVE_STICK)); //TODO: Practicality of using twist
        
        for(int i=0;i<2;i++)
        {
        	if(!lastValveButton[i] && Control.getButton(Control.SHOOT_STICK, Control.SHOOT_BUTTONS[i]))
        	{
        		shooter.toggleValve(i);
        	}
        	lastValveButton[i]=Control.getButton(Control.SHOOT_STICK,Control.SHOOT_BUTTONS[i]);
        }
        shooter.setMotors(Control.getY(Control.SHOOT_STICK));
        
        update();
    }
    
    public void update()
    {
    	driveTrain.update();
    	shooter.update();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
