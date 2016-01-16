
package org.usfirst.frc.team555.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    
    DriveTrain driveTrain;
    Shooter shooter;
    
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
    
    public void teleopPeriodic() {
        DriveMotor.PID_P = SmartDashboard.getNumber("PID-P");
        DriveMotor.PID_I = SmartDashboard.getNumber("PID-I");
        DriveMotor.PID_D = SmartDashboard.getNumber("PID-D");
        
        driveTrain.setSpeedArcade(Control.getY(Control.DRIVE_STICK), Control.getZ(Control.DRIVE_STICK));
        
        for(int i=0;i<2;i++)
        {
        	if(lastValveButton[i]==false && Control.getButton(Control.SHOOT_STICK,Control.VALVES[i])==true)
        	{
        		shooter.toggleValve(i);
        	}
        	lastValveButton[i]=Control.getButton(Control.SHOOT_STICK,Control.VALVES[i]);
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
