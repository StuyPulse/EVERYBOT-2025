package com.stuypulse.robot.util;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.odometry.Odometry;
import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Field;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public interface Clearances {
    public static boolean isClearFromReef() {
        Pose2d robotPose = Odometry.getInstance().getEstimatedPose();

        return (Field.ALLIANCE_REEF_CENTER.getDistance(robotPose.getTranslation()) >
            (Settings.Clearances.CLEARANCE_DISTANCE_FROM_REEF_PIVOT_M
            + Field.CENTER_OF_REEF_TO_REEF_FACE_M
            + Constants.Robot.LENGTH_WITH_BUMPERS_M / 2f
            - Math.hypot(Settings.Clearances.X_TOLERANCE, Settings.Clearances.Y_TOLERANCE)) &&
            
            Field.OPPOSITE_ALLIANCE_REEF_CENTER.getDistance(robotPose.getTranslation()) >
            (Settings.Clearances.CLEARANCE_DISTANCE_FROM_REEF_PIVOT_M
            + Field.CENTER_OF_REEF_TO_REEF_FACE_M
            + Constants.Robot.LENGTH_WITH_BUMPERS_M / 2f
            - Math.hypot(Settings.Clearances.X_TOLERANCE, Settings.Clearances.Y_TOLERANCE)));
    }

    public static boolean isClearFromProc() {
        Pose2d robotPose = Odometry.getInstance().getEstimatedPose();
        
        SmartDashboard.putNumber("Clearances/Opp Alliance Proc Center X", Field.OPPOSITE_ALLIANCE_PROC_CENTER.getX());

        return ((Field.ALLIANCE_PROC_CENTER.getDistance(robotPose.getTranslation()) > 
            (Settings.Clearances.CLEARANCE_DISTANCE_FROM_PROC_PIVOT_M
            + Constants.Robot.LENGTH_WITH_BUMPERS_M / 2f
            - Math.hypot(Settings.Clearances.X_TOLERANCE, Settings.Clearances.Y_TOLERANCE))) &&

            (Field.OPPOSITE_ALLIANCE_PROC_CENTER.getDistance(robotPose.getTranslation()) > 
            (Settings.Clearances.CLEARANCE_DISTANCE_FROM_PROC_PIVOT_M
            + Constants.Robot.LENGTH_WITH_BUMPERS_M / 2f
            - Math.hypot(Settings.Clearances.X_TOLERANCE, Settings.Clearances.Y_TOLERANCE)))
        );

    }
}
