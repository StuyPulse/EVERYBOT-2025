/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

/** This file contains the different ports of motors, solenoids and sensors */
public interface Ports {
    // TODO: Await port numbers from mec, put them here

    public interface Gamepad {
        int DRIVER = 0;
        int OPERATOR = 1;
        int JOYSTICK = 3; // Should this be of another port?
        int DEBUGGER = 2;
    }

    public interface LED {
        int LED_PORT = 4;
        int LED_LENGTH = 10;
    }
      
    public interface Climb {
        int CLIMB_MOTOR = 12;    
        int CLIMB_ENCODER = 12;
    }

    public interface Pivot {
        int PIVOT_MOTOR = 13;
        int ROLLER_MOTOR = 14;
    }
  
    public interface Drivetrain {
        public static final int LEFT_LEAD = 1; 
        public static final int LEFT_FOLLOW = 0;
        
        public static final int RIGHT_LEAD = 3;
        public static final int RIGHT_FOLLOW = 2;
    }
}
