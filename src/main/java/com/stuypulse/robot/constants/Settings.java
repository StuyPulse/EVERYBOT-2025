 /************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;


import edu.wpi.first.math.geometry.Rotation2d;

import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.geometry.Rotation2d;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {

    public interface Climb {
        Rotation2d STOW_ANGLE = Rotation2d.fromDegrees(90);
        Rotation2d CLIMBED_ANGLE = Rotation2d.fromDegrees(180);

        Rotation2d ANGLE_TOLERANCE = Rotation2d.fromDegrees(5);

        int CLIMB_CURRENT = 0;
    }
    
    public interface Pivot {
        SmartNumber ALGAE_HOLDING_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Rollers not spinning", 0);
        SmartNumber ALGAE_INTAKE_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Algae Intake Speed", 0);
        SmartNumber ALGAE_SHOOT_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Algae Shoot Speed", 0);
        SmartNumber CORAL_SHOOT_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Rollers not spinning", 0);

        SmartNumber PIVOT_RAISE_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Rollers not spinning", 1);
        SmartNumber PIVOT_LOWER_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Rollers not spinning", -1);
    }

    public interface Drivetrain {
        public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
    }
}
