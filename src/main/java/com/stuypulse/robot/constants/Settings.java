 /************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot.constants;

import static edu.wpi.first.units.Units.Radians;
import com.pathplanner.lib.path.PathConstraints;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.units.Units;

/**
 * File containing tunable settings for every subsystem on the robot.
 * 
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    public interface EnabledSubsystems{
        public static final SmartBoolean CLIMB = new SmartBoolean("ENABLED_SUBSYSTEMS/Is climb enabled?", true);
        public static final SmartBoolean DRIVETRAIN = new SmartBoolean("ENABLED_SUBSYSTEMS/Is drivetrain enabled?", true);
        public static final SmartBoolean PIVOT = new SmartBoolean("ENABLED_SUBSYSTEMS/Is pivot enabled?", true);
        public static final SmartBoolean PIVOT_ROLLERS = new SmartBoolean("ENABLED_SUBSYSTEMS/Is pivot rollers enabled?", true);
        public static final SmartBoolean VISION = new SmartBoolean("ENABLED_SUBSYSTEMS/Is vision enabled?", true);
    }

    public static final boolean DEBUG_MODE = true; //TODO change this to false during comps!

    public interface Climb {
        public static final Rotation2d DEFAULT_ANGLE = Rotation2d.kZero;
        public static final Rotation2d DEPLOY_ANGLE = Rotation2d.fromDegrees(-65);
        public static final Rotation2d CLIMBED_ANGLE = Rotation2d.kZero;
        
        public static final Rotation2d ANGLE_TOLERANCE = Rotation2d.fromDegrees(2);

        public static final int CLIMB_CURRENT_LIMIT = 60;
        public static final Double CLIMB_STALL_CURRENT = 15.0; //TODO find stall current
        public static final double CLIMB_STALL_DEBOUNCE = 0.01; //TODO find climb debounce

        public static final double DEFAULT_VOLTAGE = 0.0;
        public static final double CLIMB_VOLTAGE = 1.0;
        public static final double DEPLOY_VOLTAGE = -1.0;

        public static final double CLIMB_MOTOR_GEAR_RATIO = 1.0/100.0;
        public static final double CLIMB_MOTOR_REDUCTION_FACTOR = 1.0/8.0; //Found from testing
    }
    
    public interface Pivot {
        //PIVOT ANGLES
        public static final Rotation2d DEFAULT_ANGLE = Rotation2d.fromDegrees(1);
        public static final Rotation2d PIVOT_ANGLE_TOLERANCE = Rotation2d.fromDegrees(3);
        
        public static final Rotation2d CORAL_STOW_ANGLE = Rotation2d.fromDegrees(3);
        public static final Rotation2d CORAL_SCORE_ANGLE = Rotation2d.fromDegrees(29);
        public static final Rotation2d CORAL_RESEAT_ANGLE = Rotation2d.fromDegrees(29);
        
        public static final Rotation2d ALGAE_LOLLIPOP_ANGLE = Rotation2d.fromDegrees(25);
        public static final Rotation2d ALGAE_HOLDING_ANGLE = Rotation2d.fromDegrees(35);
        public static final Rotation2d ALGAE_GROUND_ANGLE = Rotation2d.fromDegrees(70);
        
        public static final Rotation2d MAX_ANGLE = Rotation2d.fromDegrees(85);

        //PIVOT MANUAL SPEEDS
        public static SmartNumber PIVOT_RAISE_SPEED = new SmartNumber("Pivot/Raise Speed", 0.2);
        public static SmartNumber PIVOT_LOWER_SPEED = new SmartNumber("Pivot/Lower Speed", -0.2);
        
        //ROLLER SPEEDS
        public static SmartNumber ROLLER_STOP_SPEED = new SmartNumber("Pivot/Roller/Stop Speed", 0);
        public static SmartNumber ALGAE_INTAKE_SPEED = new SmartNumber("Pivot/Roller/Algae/Intake Speed", -0.9);
        public static SmartNumber ALGAE_OUTTAKE_SPEED = new SmartNumber("Pivot/Roller/Algae/Outtake Speed", 0.5); 
        public static SmartNumber ALGAE_HOLD_SPEED = new SmartNumber("Pivot/Roller/Algae/Hold Speed", -0.25); 
        
        public static SmartNumber CORAL_SHOOT_SPEED = new SmartNumber("Pivot/Roller/Coral/Shoot Speed", -0.4);
        public static SmartNumber ROLLER_ROTISSERIE_SPEED = new SmartNumber("Pivot/Roller/Coral/Hold Speed", 0.17);
        
        //PIVOT MOTOR AND ROLLER CURRENT LIMIT
        public static final int PIVOT_MOTOR_CURRENT_LIMIT = 60;
        public static final int PIVOT_ROLLER_MOTOR_CURRENT_LIMIT = 60;
        
        //PIVOT STALL DETECTION
        public static final double PIVOT_STALL_CURRENT = 10; 
        public static final double PIVOT_STALL_DEBOUNCE = .25;
        
        //PIVOT CONTROL MODE VALUES
        public static final String CTRLMODE_MANUAL = "MANUAL";
        public static final String CTRLMODE_STATES = "USING_STATES";
        
        //PIVOT BUMP SWITCH
        public static final double BUMP_SWITCH_DEBOUNCE = 0.4;
        
        //AT TARGET ANGLE - ANGLE TOLERANCE
        public static final double ANGLE_TOLERANCE = 0.5;
    }

    public interface Drivetrain {
        public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
        public static final double DRIVE_UPPER_VOLTAGE_LIMIT = 12;
        public static final double DRIVE_LOWER_VOLTAGE_LIMIT = -12;

        public static final double DRIVE_FULL_SPEED_MODIFIER = 1;
        public static final double DRIVE_HALF_SPEED_MODIFIER = 0.5;
        public static final double DRIVE_DISABLE_SPEED = 0;

        public static final PathConstraints REEF_ALIGNMENT_CONSTRAINTS = new PathConstraints(
            5.4, 4.0,
            Units.Radians.convertFrom(540, Radians), Units.Radians.convertFrom(720, Radians));
        
        public static final Vector<N3> ppQelems = VecBuilder.fill(0.0125, 0.1, 0.2);
        public static final Vector<N2> ppRelems = VecBuilder.fill(1,1.2);
    }
}
