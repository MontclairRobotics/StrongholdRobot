package org.usfirst.frc.team555.robot;

import com.ni.vision.VisionException;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

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
  
  public void executeTransition(states next) {
    if (next == .cam1) {
      server.startAutomaticCapture(cam1);
    } else {
      server.startAutomaticCapture(cam2);
    }
  }
  
}
