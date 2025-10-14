package com.stuypulse.robot.constants;

import edu.wpi.first.math.util.Units;

/** Miscellaneous constants */
public interface Constants {
    public interface Climb {
        double MIN_ANGLE_DEG = -65;
        double MAX_ANGLE_DEG = 0;
    }

    public interface Pivot {
        public static final double PIVOT_MOTOR_GEAR_RATIO = 1.0/27.0;
        public static final double PIVOT_MOTOR_REDUCTION_FACTOR = 1.0/2.0;
        
        public static final double PIVOT_THROUGHBORE_RANGE = 1.0/0.241;
        public static final double PIVOT_THROUGHBORE_EXPECTED_ZERO = 1.9 + .02;
    }

    public interface Drivetrain {
        public static final double WHEEL_CIRCUMFERENCE_METERS = 0.479;
        public static final double TRACK_WIDTH_METERS = Units.inchesToMeters(21.75);
        
        public static final double DRIVETRAIN_GEAR_RATIO = 1.0/8.45;

        public static final double MAX_VELOCITY_METERS_PER_SECOND = 5.4;
        public static final double MAX_ANGULAR_VELOCITY_DEGREES_PER_SECOND = 720;
    }
    
    public interface Robot {
        public static final double LENGTH_WITH_BUMPERS_M = 1.016;
    }
}
