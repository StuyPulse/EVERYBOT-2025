 /************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.geometry.Rotation2d;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    
    public interface Pivot {
        // CHANGE ALL VALUES LATER --- ALL ARE TEMP
        Rotation2d STOW_ANGLE = Rotation2d.fromDegrees(0.0); //TEMP
        Rotation2d SCORE_ANGLE = Rotation2d.fromDegrees(0.0);
        Rotation2d PROCESSOR_SCORE_ANGLE = Rotation2d.fromDegrees(0.0); // TEMP
        Rotation2d GROUND_PICKUP = Rotation2d.fromDegrees(0.0); // TEMP
        Rotation2d RELEASE_ANGLE = Rotation2d.fromDegrees(0.0); // CHANGE VALUE LATER
        double DEFAULT_VOLTAGE = 0.0; // CHANGE LATER
        double ANGLE_ERROR = 0.0; // CHANGE LATER
    }
    public interface Rollers {
        SmartNumber ALGAE_STAYING_STILL_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Rollers not spinning", 0);
        SmartNumber ALGAE_INTAKE_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Algae Intake Speed", 0);
        SmartNumber ALGAE_SHOOT_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Algae Shoot Speed", 0);
    }
    

}
