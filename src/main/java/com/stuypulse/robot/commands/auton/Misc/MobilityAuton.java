/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot.commands.auton.misc;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.constants.Settings.LEDPatterns;

/**
 * Move for the Ranking Point
 * 
 * @author Sebastian Waldman
 */
public class MobilityAuton extends SequentialCommandGroup {
    public MobilityAuton() {
        addCommands(
            new LEDApplyPattern(LEDPatterns.MOBILITY_AUTON),
            new DriveTank(.25, .25, true),
            new WaitUntilCommand(1.00),
            new DriveTank(0, 0, true)
        );
    }
}
