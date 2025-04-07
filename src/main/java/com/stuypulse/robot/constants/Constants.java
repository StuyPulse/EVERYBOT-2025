package com.stuypulse.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;

public interface Constants {

    public interface Climb {
        Rotation2d MIN_ANGLE = Rotation2d.kZero;
        Rotation2d MAX_ANGLE = Rotation2d.fromDegrees(0.0);

        Rotation2d CLIMBER_OFFSET = Rotation2d.fromDegrees(0.0 - 0.0);
    }

    public interface Drivetrain {
        //Make sure this converts the rotations to meters
        public static final double WHEEL_CIRCUMFERENCE = 3.0;
    }
}