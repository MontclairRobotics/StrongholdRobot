package org.usfirst.frc.team555.robot;

import com.ni.vision.VisionException;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * CameraViewer.java
 *
 * Joshua Rapoport 2016
 *
 * This is a state machine designed to flip between two USB cameras (identified by name).
 * Accomplished by pressing the buttons on the Joystick identified as 3 and 4.
 * Implemented in Robot.java
 * Results should appear in SmartDashboard as a live feed of either camera.
 **
 */

enum states {cam1, cam2}

public class CameraViewer extends StateMachine<states> {
  
  public static final int CAM_WIDTH  = 640;
  public static final int CAM_HEIGHT = 480;
  public static final int CAM_FPS = 16;
  
  CameraServer server;
  USBCamera camera1, camera2;
  
  public CameraView(String name1, String name2) {
    super(states.cam1);
    
    try {
      camera1 = newCamera(name1);
      camera2 = newCamera(name2);
      
      server = CameraServer.getInstance();
      server.startAutomaticCapture(camera1);
    } catch (VisionException ex) {
      ex.printStackTrace();
    }
  }
  
  private USBCamera newCamera(String name) {
    USBCamera newCam = new USBCamera(name);
    newCam.setSize(CAM_WIDTH, CAM_HEIGHT);
    newCam.setFPS(CAM_FPS);
    
    return newCam;
  }
  
  public USBCamera currentCamera() {
    return (currentState == cam1) ? camera1 : camera2;
  }
  
  public states calculateNextState() {
    return (currentState == cam1) ? cam2 : cam1;
  }
  
  public void update() {
    if (Control.getButton(Control.DRIVE_STICK, 3) && currentState != cam1) {
      executeTransition(cam1);
    } else if (Control.getButton(Control.DRIVE_STICK, 4) && currentState != cam2) {
      executeTransition(cam2);
    }
  }
  
  public void executeTransition(states next) {
    if (next == cam1) {
      server.startAutomaticCapture(camera1);
    } else {
      server.startAutomaticCapture(camera2);
    }
  }
  
}
