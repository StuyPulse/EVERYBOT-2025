package com.stuypulse.robot.constants;

import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.RobotConfig;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

/** Miscellaneous constants */
public interface Constants {

    public interface Climb {
        Rotation2d MIN_ANGLE = Rotation2d.kZero;
        Rotation2d MAX_ANGLE = Rotation2d.fromDegrees(120);
    }

    public interface Pivot {
        public static final double PIVOT_MOTOR_GEAR_RATIO = 1.0/27.0;
        public static final double PIVOT_MOTOR_REDUCTION_FACTOR = 1.0/2.0;
        
        public static final double PIVOT_THROUGHBORE_RANGE = 1.0/0.241;
        public static final double PIVOT_THROUGHBORE_EXPECTED_ZERO = 1.9;
    }

    public interface Drivetrain {
        
        public static final double WHEEL_CIRCUMFERENCE_METERS = 0.479;
        public static final double TRACK_WIDTH_METERS = Units.inchesToMeters(21.75);
        
        public static final double DRIVETRAIN_GEAR_RATIO = 1.0/8.45;
        
        public static final double DISTANCE_PER_PULSE = WHEEL_CIRCUMFERENCE_METERS * DRIVETRAIN_GEAR_RATIO / 42.0; //42.0 represents the pulse per motor shaft reduction (42 for NEO)
        public static final double MAX_VELOCITY_METERS_PER_SECOND = 5.4;
        
        // public static final RobotConfig config = null;
        // try{
        //     config = RobotConfig.fromGUISettings();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        
    }
    
    public interface Autonomous {
        //TODO: FIND AUTONOMOUS SPEED CONSTRAINTS
        public static final double MAX_SPEED_METERS_PER_SECOND = 10.0; 
        public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = 100.0;
    }
}
