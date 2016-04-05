package org.usfirst.frc.team555.robot;

import com.ni.vision.VisionException;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

/*
 * CameraView.java
 *
 * Joshua Rapoport 2016
 *
 * This is a state machine designed to flip between two USB cameras (identified by name).
 * Accomplished by pressing the buttons on the Joystick identified as 3 and 4.
 * Implemented in Robot.java
 * Results should appear in SmartDashboard as a live feed of either camera.
 * 
 */

enum states {cam1, cam2}

public class CameraView extends StateMachine<states> {
  
  public CameraServer server;
  USBCamera camera1, camera2;
  
  public CameraView(String name1, String name2) {
    super(states.cam1);
    
    try {
      camera1 = USBCamera(name1);
      camera2 = USBCamera(name2);
      
      server = CameraServer.getInstance();
      server.startAutomaticCapture(camera);
    } catch (VisionException ex) {
      ex.printStackTrace();
    }
  }
  
  public states calculateNextState() {
    return (currentState == cam1) ? .cam2 : .cam1;
  }
  
  public void update() {
    if (Control.getButton(Control.DRIVE_STICK, 3) && currentState != .cam1) {
      executeTransition(.cam1);
    } else if (Control.getButton(Control.DRIVE_STICK, 4) && currentState != .cam2) {
      executeTransition(.cam2);
    }
  }
  
  public void executeTransition(states next) {
    if (next == .cam1) {
      server.startAutomaticCapture(cam1);
    } else {
      server.startAutomaticCapture(cam2);
    }
  }
  
}
