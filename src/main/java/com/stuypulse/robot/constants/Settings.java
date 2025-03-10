/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.network.SmartBoolean;
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
    Rotation2d STOW_ANGLE = Rotation2d.fromDegrees(0);
    Rotation2d EXTEND_ANGLE = Rotation2d.fromDegrees(0);
    Rotation2d CLIMBED_ANGLE = Rotation2d.fromDegrees(0);
    int CLIMB_CURRENT = 0;
}

}
