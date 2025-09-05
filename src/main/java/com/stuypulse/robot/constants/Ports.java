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
        int DEBUGGER = 2;
    }
      
    public interface Climb {
        int CLIMB_MOTOR = 2;
        int CLIMB_ENCODER = 2;
    }

    public interface Pivot {
        int PIVOT_MOTOR = 1;
        int ROLLER_MOTOR = 3;
        int THROUGHBORE_DIO = 5;
        int BUMP_SWITCH = 4;
    }
  
    public interface Drivetrain {
        public static final int GYRO = 1;

        public static final int LEFT_LEAD = 4; 
        public static final int LEFT_FOLLOW = 5;
        
        public static final int RIGHT_LEAD = 6;
        public static final int RIGHT_FOLLOW = 7;
    }
    public interface  BusIDS {
        public static final int climbbusid = 0;
        public static final int driveRightLead = 0;
        public static final int driveRightFollow = 0;
        public static final int driveLeftLead = 0;
        public static final int driveLeftFollow = 0;
        public static final int pivot = 0;
        public static final int roller = 0;
    }
}
