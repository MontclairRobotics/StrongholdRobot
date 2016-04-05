package org.usfirst.frc.team555.robot;

import com.ni.vision.VisionException;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

enum state {cam1, cam2}

public class CameraView extends StateMachine<state> {
  
  public CameraServer server;
  USBCamera camera1, camera2;
  
  public CameraView(String name1, String name2) {
    super(state.cam1);
    
    try {
      camera1 = USBCamera(name1);
      camera2 = USBCamera(name2);
      
      server = CameraServer.getInstance();
      server.startAutomaticCapture(camera);
    } catch (VisionException ex){
      ex.printStackTrace();
  	}
  }
  
  public state calculateNextState() {
    return (currentState == cam1) ? state.cam2 : state.cam1;
  }
  
  public void executeTransition(state next) {
    switch (next) {
      case .cam1:
        server.startAutomaticCapture(cam2);
        break;
      case .cam2:
        server.startAutomaticCapture(cam1);
        break;
      default: break;
    }
  }
  
}
