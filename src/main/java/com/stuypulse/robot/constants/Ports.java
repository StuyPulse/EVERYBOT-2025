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
        int CLIMB_MOTOR = 5;    
        int CLIMB_ENCODER = 6;
    }

    public interface Pivot {
        int PIVOT_MOTOR = 7;
        int ROLLER_MOTOR = 8;
    }
  
    public interface Drivetrain {
        public static final int LEFT_LEAD = 9; 
        public static final int LEFT_FOLLOW = 10;
        
        public static final int RIGHT_LEAD = 11;
        public static final int RIGHT_FOLLOW = 12;
    }
}
