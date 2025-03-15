/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.PivotCoralOut;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;


/*
 * Move for RP; thats it bruh
 *
 * @author Sebastian Waldman
 * I hope this works first try :pray:
 */
public class OneL1Auton extends SequentialCommandGroup {

    public OneL1Auton() {
        addCommands(
            new LEDApplyPattern(LEDPattern.rainbow(250,250).blink(Units.Seconds.of(1.5))),
            new DriveTank(Drivetrain.getInstance(), .25, .25, true),
            new WaitUntilCommand(7.00),
            new DriveTank(Drivetrain.getInstance(), 0, 0, true),
            new PivotCoralOut()
        );
    }
}