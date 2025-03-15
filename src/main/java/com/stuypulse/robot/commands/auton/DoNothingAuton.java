/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.commands.leds.LEDApplyPattern;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/*-
 * This auton does nothing... it is used as a placeholder
 *
 * @author Sam Belliveau
 * edited by sebastian waldman
 */
public class DoNothingAuton extends SequentialCommandGroup {

    public DoNothingAuton() {
        addCommands(
            new LEDApplyPattern(LEDPattern.solid(Color.kWheat).blink(Units.Seconds.of(1.5)))
                /** Do a whole lot of nothing */
                );
    }
}