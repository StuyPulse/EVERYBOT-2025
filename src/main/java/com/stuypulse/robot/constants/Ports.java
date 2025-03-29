/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

/** This file contains the different ports of motors, solenoids and sensors */
public interface Ports {
    // TODO: Await CAN IDs / Device IDs from mec, put them here

    public interface Gamepad {
        int DRIVER = 0;
        int OPERATOR = 1;
       // int JOYSTICK = 3; // Should this be of another port?
        int DEBUGGER = 2;
    }

    public interface LED {
        int LED_PORT = 4;
        int LED_LENGTH = 10;
    }
      
    public interface Climb {
        int CLIMB_MOTOR = 2;
        int CLIMB_ENCODER = 2;
    }

    public interface Pivot {
        int PIVOT_MOTOR = 1;
        int ROLLER_MOTOR = 3;
    }
  
    public interface Drivetrain {
        // NEO
        public static final int LEFT_LEAD = 4; 
        // CIM
        public static final int LEFT_FOLLOW = 5;
        

        // TODO: Right is not turning.
        // NEO
        public static final int RIGHT_LEAD = 6;
        // CIM
        public static final int RIGHT_FOLLOW = 7;
    }
}
