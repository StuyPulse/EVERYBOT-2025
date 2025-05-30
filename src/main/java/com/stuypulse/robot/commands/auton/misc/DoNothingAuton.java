/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot.commands.auton.misc;

import com.stuypulse.robot.constants.Settings.LEDPatterns;

import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * This auton does nothing... it is used as a placeholder
 *
 * @author Sam Belliveau, 
 * edited by Sebastian Waldman
 */
public class DoNothingAuton extends SequentialCommandGroup {
    public DoNothingAuton() {
        addCommands(
            new LEDApplyPattern(LEDPatterns.DO_NOTHING_AUTON)
                /** Do a whole lot of nothing */
                );
    }
}
