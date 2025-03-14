package com.stuypulse.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;

public interface Constants {

    public interface Climb {
        Rotation2d MIN_ANGLE = Rotation2d.kZero;
        Rotation2d MAX_ANGLE = Rotation2d.fromDegrees(0.0);

        Rotation2d CLIMBER_OFFSET = Rotation2d.fromDegrees(0.0 - 0.0);
        double STOW_VOLTAGE = 0.0;
        double EXTEND_VOLTAGE = 0.0;
        double CLIMBING_VOLTAGE = 0.0;
        double DEFAULT_VOLTAGE = 0.0;

        double ANGLE_TOLERANCE = 0.0;
    }
}