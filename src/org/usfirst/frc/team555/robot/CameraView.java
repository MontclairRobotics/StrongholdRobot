package org.usfirst.frc.team555.robot;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

enum state {cam1, cam2}

public class CameraView extends StateMachine<state> {
  
  public CameraServer server;
  USBCamera cam1, cam2;
  
  public CameraView(String name1, String name2) {
    super(state.cam1);
    
    try {
      server = CameraServer.getInstance();
      
      cam1 = USBCamera(name1);
      cam2 = USBCamera(name2);
      server.
    } catch (VisionException ex){
      ex.printStackTrace();
  	}
  }
  
  public state calculateNextState() {
    return (currentState == cam1) ? state.cam2 : state.cam1
  }
  
  public void executeTransition(state next) {
    switch (next) {
      case .cam1:
        break
      case .cam2:
        break;
      default: break;
    }
  }
  
  public void executeCurrentState() {
    switch (currentState) {
      case .cam1:
        break
      case .cam2:
        break;
      default: break;
    }
  }
  
}
