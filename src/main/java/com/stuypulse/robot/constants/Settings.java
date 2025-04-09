 /************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;


import edu.wpi.first.math.geometry.Rotation2d;

import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;


/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {

    public interface LEDPatterns {
        // Auton Colors
        LEDPattern DO_NOTHING_AUTON = LEDPattern.solid(Color.kWheat).blink(Units.Seconds.of(1.5));
        LEDPattern MOBILITY_AUTON = LEDPattern.solid(Color.kHoneydew).blink(Units.Seconds.of(1.5));
        LEDPattern SINGLE_L1_AUTON = LEDPattern.rainbow(250,250).scrollAtAbsoluteSpeed(Units.MetersPerSecond.of(1), Units.Meters.of(1 / 200.0));
        LEDPattern TWO_L1_AUTON = LEDPattern.rainbow(250,250).blink(Units.Seconds.of(1.5)).reversed();
        LEDPattern PUSH_BACKWARDS_SINGLE_L1_AUTON = LEDPattern.solid(Color.kSeaGreen).blink(Units.Seconds.of(1.5));

        // Alliance Colors
        LEDPattern RED_ALLIANCE = LEDPattern.solid(Color.kRed);
        LEDPattern BLUE_ALLIANCE = LEDPattern.solid(Color.kBlue);
        LEDPattern NO_ALLIANCE = LEDPattern.solid(Color.kGreen);

        // Robot State Colors
        LEDPattern CLIMBING = LEDPattern.solid(Color.kGold).blink(Units.Seconds.of(1.5));

        // Button-Related Colors
        LEDPattern CORAL_OUT = LEDPattern.solid(Color.kWhite).blink(Units.Seconds.of(1.5));
        LEDPattern ALGAE_IN = LEDPattern.solid(Color.kAqua);
        LEDPattern ALGAE_OUT = LEDPattern.solid(Color.kAqua).blink(Units.Seconds.of(1.5));
    }

    public interface Climb {
        Rotation2d STOW_ANGLE = Rotation2d.fromDegrees(0);
        Rotation2d CLIMBED_ANGLE = Rotation2d.fromDegrees(180);

        Rotation2d ANGLE_TOLERANCE = Rotation2d.fromDegrees(5);

        int CLIMB_CURRENT = 0;

        double STOW_VOLTAGE = 0.0;
        double EXTEND_VOLTAGE = 0.0;
        double CLIMBING_VOLTAGE = 0.0;
        double DEFAULT_VOLTAGE = 0.0;
    }
    
    public interface Pivot {
        SmartNumber ALGAE_HOLDING_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Rollers not spinning", 1);
        SmartNumber ALGAE_INTAKE_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Algae Intake Speed", -1);

        SmartNumber ALGAE_SHOOT_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Algae Shoot Speed", 1);
        SmartNumber CORAL_SHOOT_SPEED = new SmartNumber("Pivot/Roller/Target Speeds/Rollers not spinning", -0.75);
        SmartNumber ROLLER_STOP_SPEED = new SmartNumber("Pivot Roller Stop Speed", 0);
        SmartNumber ROLLER_ROTISSERIE_SPEED = new SmartNumber("Roller Coral Hold Speed", 0.08);

        SmartNumber PIVOT_RAISE_SPEED = new SmartNumber("Pivot Raise Speed", 0.17);
        SmartNumber PIVOT_LOWER_SPEED = new SmartNumber("Pivot Lower Speed", -0.17);
        
        public static final int PIVOT_MOTOR_CURRENT_LIMIT = 60;
        public static final int PIVOT_ROLLER_MOTOR_CURRENT_LIMIT = 60;

        public static final double PIVOT_MOTOR_GEAR_RATIO = 1.0/27.0;
        public static final double PIVOT_MOTOR_REDUCTION_FACTOR = 1.0/2.0;

        Rotation2d DEFAULT_ANGLE = Rotation2d.fromDegrees(0);
        Rotation2d CORAL_STOW_ANGLE = Rotation2d.fromDegrees(-4); 
        Rotation2d ALGAE_HOLDING_ANGLE = Rotation2d.fromDegrees(75);
        Rotation2d ALGAE_INTAKE_ANGLE = Rotation2d.fromDegrees(45);
        Rotation2d MAX_ANGLE = Rotation2d.fromDegrees(0); //TODO: FIND MAX HARD STOP ANGLE
    }


    public interface Drivetrain {
        public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
        public static final double TRACK_WIDTH = 0.0;
    }

    // public interface DriveMode {
    //     SmartString GAMEPAD = new SmartString("Drivetrain/Controller/Mode", "GAMEPAD"); // Gamepad or Joystick
    // } skibidi si
}
