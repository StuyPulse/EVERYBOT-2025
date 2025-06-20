/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot.commands.oldAutons.misc;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * This auton does nothing... it is used as a placeholder
 *
 * @author Sam Belliveau
 */
public class DoNothingAuton extends SequentialCommandGroup {
    public DoNothingAuton() {
        addCommands(
                /** Do a whole lot of nothing */
                );
    }
}
