package com.stuypulse.robot.util;

import com.stuypulse.robot.subsystems.vision.LimelightVision;
import com.stuypulse.robot.util.vision.LimelightHelpers;

import edu.wpi.first.math.geometry.Pose2d;

public class Reefutil {
    private Pose2d pose = LimelightVision.getInstance().getEstimatedPose();

    public enum CoralFaces{
        
    }
}
