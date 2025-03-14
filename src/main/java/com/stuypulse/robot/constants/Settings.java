/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.Units.*;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    public interface LEDPatterns {
        // Alliance Colors
        LEDPattern RED_ALLIANCE = LEDPattern.solid(Color.kRed);
        LEDPattern BLUE_ALLIANCE = LEDPattern.solid(Color.kBlue);
        LEDPattern NO_ALLIANCE = LEDPattern.solid(Color.kGreen);

        // Robot State Colors
        LEDPattern CLIMBED = LEDPattern.rainbow(255, 128);

        // Button-Related Colors

    }

    public interface Drivetrain {
        public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
    }
}
