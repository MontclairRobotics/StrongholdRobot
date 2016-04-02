package org.usfirst.frc.team555.robot;

import com.kauailabs.navx.frc.AHRS;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Robot extends IterativeRobot {
	
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser, obstacleChooser, cameraEnabler;
    int ticks = 0;
    
    public static final boolean USBcamera = true;
    
    public static DriveTrain driveTrain;
    public static Shooter shooter;
    //AutoShooter autoShooter;
    //SocketManager netManager;
    
    AHRS ahrs;
    NavXAccelerometer accel;
    public static NavXGyro gyro;
    //private ShooterMotor leftShoot = new ShooterMotor(0);
    //private ShooterMotor rightShoot = new ShooterMotor(1);
    //PitchCorrector corrector;
    
    public static HTTP coordServer;
    public static boolean auto=false;
    
    public static SmartDashboard dashboard;
    Thread dashboardThread;
    
    boolean[] lastValveButton;
    
    StateMachine<?> autoProgram;
    
    public USBCamera camera;
    public CameraServer server;
    Compressor compressor;
    boolean compressorToggle = false;

    
    public void robotInit() {
    	dashboard = new SmartDashboard();
    	//dashboardThread = new Thread(dashboard);
    	//dashboardThread.start();
    	
    	chooser = new SendableChooser();
        chooser.addDefault("Forward", new Autonomous0());
        chooser.addObject("Adjustable", new AutonomousAdjustable());
        chooser.addObject("Disabled", null);
        dashboard.putData("Auto choices", chooser);
        
        obstacleChooser = new SendableChooser();
        obstacleChooser.addDefault("none", obstacles.none);
        obstacleChooser.addObject("cheval", obstacles.chev);
        obstacleChooser.addObject("low bar", obstacles.lowBar);
        obstacleChooser.addObject("moat", obstacles.moat);
        obstacleChooser.addObject("ramps", obstacles.ramps);
        obstacleChooser.addObject("terrain", obstacles.terrain);
        obstacleChooser.addObject("wall", obstacles.wall);
        dashboard.putData("Obstacle choices", obstacleChooser);
        
        cameraEnabler = new SendableChooser();
        cameraEnabler.addDefault("Enabled", true);
        cameraEnabler.addObject("Disabled", false);
        dashboard.putData("Camera enabler", cameraEnabler);
        
        dashboard.putNumber("PID-P", DriveMotor.PID_P);
        dashboard.putNumber("PID-I", DriveMotor.PID_I);
        dashboard.putNumber("PID-D", DriveMotor.PID_D);
        dashboard.putNumber("auto position", 0);
        
        ahrs = new AHRS(SPI.Port.kMXP);
        accel = new NavXAccelerometer(ahrs);
        gyro = new NavXGyro(ahrs);

        driveTrain = new DriveTrain();
        
        shooter = new Shooter(driveTrain);
        if(USBcamera) {
        	camera = new USBCamera("cam1");
        	//camera.setSize(640, 480);
        	server = CameraServer.getInstance();
        	server.startAutomaticCapture(camera);
        }
        
        compressor = new Compressor(0);
        compressor.start();
        //autoShooter=new AutoShooter(shooter);
        
        //netManager = new SocketManager();
        
        //corrector = new PitchCorrector(gyro, driveTrain);
    }

    public void autonomousInit() {
    	//autoSelected = (String) chooser.getSelected();
//		autoSelected = dashboard.getString("Auto Selector", defaultAuto);
		//System.out.println("Auto selected: " + autoSelected);
		//driveTrain.setDistance(500, 10, 0);//PUT IN REAL VALUES
		auto = true;
		if(chooser.getSelected() == null) {
			auto = false;
			return;
		}
		compressor.start();
		autoProgram = (StateMachine<?>)chooser.getSelected();
		if(autoProgram instanceof AutonomousAdjustable) {
			obstacles mode = (obstacles) obstacleChooser.getSelected();
			int pos = (int) dashboard.getNumber("auto position");
			boolean dir;
			switch(mode) {
			case none:
				dir = true;
				break;
			default:
				dir = true;
				break;
			}
			autoProgram = new AutonomousAdjustable(dir, pos, mode);
		}
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	/*switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}*/
    	if(!auto) return;
    	((StateMachine<?>)chooser.getSelected()).update();
    	driveTrain.update();
    	
    }
    
    /**
     * This function is called periodically during operator control
     */
    
    public void teleopInit() {
    	DriveMotor.PID_P = dashboard.getNumber("PID-P");
        DriveMotor.PID_I = dashboard.getNumber("PID-I");
        DriveMotor.PID_D = dashboard.getNumber("PID-D");
        try {
        	coordServer = new HTTP(5805,"coords");
        } catch(Exception e) {
        	e.printStackTrace();
        }
        auto = false;
    }
    
    public void teleopPeriodic() {
    	driveTrain.setSpeedXY(Control.getX(Control.DRIVE_STICK), -Control.getY(Control.DRIVE_STICK));
    	driveTrain.setLock(Control.getButton(Control.DRIVE_STICK,Control.LOCK_BUTTON));
        //shooter.setActive(Control.getButton(Control.DRIVE_STICK,Control.SHOOT_AUTOTARGET));
        //shooter.setLift(Control.getButton(Control.SHOOT_STICK,Control.SHOOT_UP),
        	//	Control.getButton(Control.SHOOT_STICK,Control.SHOOT_DOWN));
    	
    	
        shooter.setJoystick(Control.getX(Control.SHOOT_STICK),-Control.getY(Control.SHOOT_STICK),Control.getButton(Control.SHOOT_STICK,Control.SHOOT_OVERRIDE));
        
        //leftShoot.setSpeed(Control.getY(Control.SHOOT_STICK));
        //rightShoot.setSpeed(Control.getY(Control.SHOOT_STICK)*-1);
        update();
    }
    
    public void update() {
    	driveTrain.update();
    	//autoShooter.update();
    	
    	
    	shooter.update();
    	
    	
    	if (Control.getButton(Control.DRIVE_STICK, 3)) {
    		driveTrain.backwards = true;
    		
    	} 
    	else  {
    		driveTrain.backwards = false;
    	}
    	dashboard.putNumber("gyro-angle", gyro.getYaw());
    	dashboard.putNumber("accel-x", accel.getAccelX());
    	dashboard.putNumber("accel-y", accel.getAccelY());
    	dashboard.putNumber("accel-z", accel.getAccelZ());
    	dashboard.putNumber("accel-yaw", gyro.getYaw());
    	//int x=(int)((1+Control.getX(Control.DRIVE_STICK))*(320/2));
    	//int y=(int)((1+Control.getY(Control.DRIVE_STICK))*(240/2));
    	//coordServer.setResponse(x+","+y);
    	
    	/*if(Control.getButton(Control.DRIVE_STICK, 12) && !compressorToggle) {
    		compressorToggle = true;
    		if(compressor.enabled()) {
    			compressor.stop();
    		} else {
    			compressor.start();
    		}
    	} else if(compressorToggle && !Control.getButton(Control.DRIVE_STICK, 12)) {
    		compressorToggle = false;
    	}
    	if(compressorToggle){
    		dashboard.putString("compressor", "TRUE");
    	}
    	else{
    		dashboard.putString("compressor", "FALSE");
    	}*/
    }
    
    public void disabledInit() {
    	if(coordServer != null) {
    		coordServer.stop();
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void backwards(){
    	 
    }
    
    
}

