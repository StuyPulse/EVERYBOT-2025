/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot.commands.auton.coral;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.pivot.SetPivotControlMode;
import com.stuypulse.robot.commands.pivot.PivotCombos.PivotCoralScore;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotControlMode;

/**
 * attempts to score one coral
 * I hope this works first try :pray:
 * 
 * @author Sebastian Waldman
 */
public class SingleCoralAuton extends SequentialCommandGroup {
    public SingleCoralAuton() {
        addCommands(
            new DriveTank(-.45, -.45, true)
                .withTimeout(2.5),
            new DriveTank(0, 0, false)
                .withTimeout(0.1),
            new PivotCoralScore()
                 .withTimeout(5.0)
        );
    }
}
