/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

/** This file contains the different ports of motors, solenoids and sensors */
public interface Ports {

    public interface Gamepad {
        int DRIVER = 0;
        int OPERATOR = 1;
        int JOYSTICK = 3;
        int DEBUGGER = 2;
    }

    public interface LED {
        int LED_PORT = 3;
        int LED_LENGTH = 10;
    }
      
    public interface Climb {
        int CLIMB_MOTOR = 0;    
        int CLIMB_ENCODER = 1;
    }

    public interface Pivot {
        int PIVOT_MOTOR = 0; // PUT REAL VALUE LATER
        int ROLLER_MOTOR = 0; // PUT REAL VALUE LATER
    }
  
    public interface Drivetrain {
        public static final int LEFT_LEAD = 0; 
        public static final int LEFT_FOLLOW = 1;
        
        public static final int RIGHT_LEAD = 2;
        public static final int RIGHT_FOLLOW = 3;
    }
}
