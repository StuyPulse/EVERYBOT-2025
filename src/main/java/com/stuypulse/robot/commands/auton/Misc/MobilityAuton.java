/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.constants.Settings.LEDPatterns;


/*
 * Move for RP; thats it bruh
 *
 * @author Sebastian Waldman, Yunu and luc (we so skilled)
 * HELLO GANAG :smiling_imp: :fire: :tired_face: , WE FINISH ROBOT SLKDJF :100: :pray:
OHHHH DEAR MASTERS OF GIIT :pray: :pray: :pray: , PLEASE REVIEW SLFD :smiling_imp: :fire: :tired_face: :weary: :100: :first_place_medal: :pray: :pray:
THANQE YOUE :100: :+1: :pray: :1234:
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