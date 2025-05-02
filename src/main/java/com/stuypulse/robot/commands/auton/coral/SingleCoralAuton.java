/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot.commands.auton.coral;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerStop;
import com.stuypulse.robot.commands.pivot.roller.PivotCoralOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerToDirection;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.LEDPatterns;

/**
 * attempts to score one coral
 * I hope this works first try :pray:
 * 
 * @author Sebastian Waldman
 */
public class SingleCoralAuton extends SequentialCommandGroup {
    public SingleCoralAuton() {
        addCommands(
            new DriveTank(-.25, -.25, true)
                .withTimeout(7),
            new DriveTank(0, 0, true)
                .withTimeout(0.1),
            new PivotCoralOuttake()
                .withTimeout(3),
            new PivotRollerStop()
                .withTimeout(0.1)
        );
    }
}
