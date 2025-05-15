 /************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

/**
 * File containing tunable settings for every subsystem on the robot.
 * 
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    public interface LEDPatterns {
        // AUTON COLORS    
        // MISC
        public static final LEDPattern DO_NOTHING_AUTON = LEDPattern.solid(Color.kWheat).blink(Units.Seconds.of(1.5));
        public static final LEDPattern MOBILITY_AUTON = LEDPattern.solid(Color.kHoneydew).blink(Units.Seconds.of(1.5));

        // CORAL ONLY
        public static final LEDPattern SINGLE_L1_AUTON = LEDPattern.rainbow(250,250).scrollAtAbsoluteSpeed(Units.MetersPerSecond.of(1), Units.Meters.of(1 / 200.0));
        public static final LEDPattern TWO_L1_AUTON = LEDPattern.rainbow(250,250).blink(Units.Seconds.of(1.5)).reversed();

        // PUSH ONLY
        public static final LEDPattern PUSH_FORWARDS_AUTON = LEDPattern.solid(Color.kLawnGreen).blink(Units.Seconds.of(1.5));
        public static final LEDPattern PUSH_BACKWARDS_AUTON = LEDPattern.solid(Color.kDarkSeaGreen).blink(Units.Seconds.of(1.5));

        // COMBINATIONS
        public static final LEDPattern PUSH_BACKWARDS_SINGLE_L1_AUTON = LEDPattern.solid(Color.kSeaGreen).blink(Units.Seconds.of(1.5));

        // Alliance Colors
        public static final LEDPattern RED_ALLIANCE = LEDPattern.solid(Color.kRed);
        public static final LEDPattern BLUE_ALLIANCE = LEDPattern.solid(Color.kBlue);
        public static final LEDPattern NO_ALLIANCE = LEDPattern.solid(Color.kGreen);

        // Robot State Colors
        public static final LEDPattern CLIMBING = LEDPattern.solid(Color.kGold).blink(Units.Seconds.of(1.5));

        // Button-Related Colors
        public static final LEDPattern CORAL_OUT = LEDPattern.solid(Color.kWhite).blink(Units.Seconds.of(1.5));
        public static final LEDPattern ALGAE_IN = LEDPattern.solid(Color.kAqua);
        public static final LEDPattern ALGAE_OUT = LEDPattern.solid(Color.kAqua).blink(Units.Seconds.of(1.5));
    }

    public interface Climb {
        public static final Rotation2d DEFAULT_ANGLE = Rotation2d.fromDegrees(0);
        public static final Rotation2d STOW_ANGLE = Rotation2d.fromDegrees(0);
        public static final Rotation2d CLIMBED_ANGLE = Rotation2d.fromDegrees(75);
        
        public static final Rotation2d ANGLE_TOLERANCE = Rotation2d.fromDegrees(2);

        public static final int CLIMB_CURRENT_LIMIT = 60;

        public static final double DEFAULT_VOLTAGE = 0.0;
        public static final double STOW_VOLTAGE = -1.0;
        public static final double CLIMBING_VOLTAGE = 1.0;

        public static final double CLIMB_MOTOR_GEAR_RATIO = 1.0/100.0;
        public static final double CLIMB_MOTOR_REDUCTION_FACTOR = 1.0/8.0; //Found from testing
    }
    
    public interface Pivot { 
        //PIVOT ANGLES
        public static final Rotation2d DEFAULT_ANGLE = Rotation2d.fromDegrees(0);
        
        public static final Rotation2d CORAL_STOW_ANGLE = Rotation2d.fromDegrees(3);
        public static final Rotation2d CORAL_SCORE_ANGLE = Rotation2d.fromDegrees(29);
        public static final Rotation2d CORAL_RESEAT_ANGLE = Rotation2d.fromDegrees(29);
        
        public static final Rotation2d ALGAE_LOLLIPOP_ANGLE = Rotation2d.fromDegrees(25);
        public static final Rotation2d ALGAE_HOLDING_ANGLE = Rotation2d.fromDegrees(45);
        public static final Rotation2d ALGAE_GROUND_ANGLE = Rotation2d.fromDegrees(70);
        
        public static final Rotation2d MAX_ANGLE = Rotation2d.fromDegrees(85);

        //PIVOT MANUAL SPEEDS
        public static SmartNumber PIVOT_RAISE_SPEED = new SmartNumber("Pivot/Raise Speed", -0.2);
        public static SmartNumber PIVOT_LOWER_SPEED = new SmartNumber("Pivot/Lower Speed", 0.2);
        
        //ROLLER SPEEDS
        public static SmartNumber ROLLER_STOP_SPEED = new SmartNumber("Pivot/Roller/Stop Speed", 0);
        public static SmartNumber ALGAE_INTAKE_SPEED = new SmartNumber("Pivot/Roller/Algae/Intake Speed", -0.85); // TODO: Test
        public static SmartNumber ALGAE_OUTTAKE_SPEED = new SmartNumber("Pivot/Roller/Algae/Outtake Speed", 0.5); // TODO: Test
        public static SmartNumber ALGAE_HOLD_SPEED = new SmartNumber("Pivot/Roller/Algae/Hold Speed", -0.1); // TODO: Test
        
        public static SmartNumber CORAL_SHOOT_SPEED = new SmartNumber("Pivot/Roller/Coral/Shoot Speed", -0.4);
        public static SmartNumber ROLLER_ROTISSERIE_SPEED = new SmartNumber("Pivot/Roller/Coral/Hold Speed", 0.1);
        
        //PIVOT MOTOR AND ROLLER CURRENT LIMIT
        public static final int PIVOT_MOTOR_CURRENT_LIMIT = 60;
        public static final int PIVOT_ROLLER_MOTOR_CURRENT_LIMIT = 60;
        
        //PIVOT STALL DETECTION
        public static final double PIVOT_STALL_CURRENT = 10; 
        public static final double PIVOT_STALL_DEBOUNCE = .25;
        
        //PIVOT CONTROL MODE VALUES
        public static final String CTRLMODE_MANUAL = "MANUAL";
        public static final String CTRLMODE_STATES = "USING_STATES";
    }

    public interface Drivetrain {
        public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
    }
}
