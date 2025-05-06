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
            new DriveTank(-.25, -.25, true)
                .withTimeout(5),
            new DriveTank(0,0,true)
                .withTimeout(0.1)
        );
    }
}
