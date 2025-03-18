/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.PivotCoralOut;
import com.stuypulse.robot.constants.Settings.LEDPatterns;


/*
 * Move for RP; thats it bruh
 *
 * @author Sebastian Waldman
 * I hope this works first try :pray:
 */
public class SingleL1Auton extends SequentialCommandGroup {

    public SingleL1Auton() {
        addCommands(
            new LEDApplyPattern(LEDPatterns.SINGLE_L1_AUTON),
            new DriveTank(.25, .25, true),
            new WaitUntilCommand(5.00),
            new DriveTank(0, 0, true),
            new PivotCoralOut()
        );
    }
}