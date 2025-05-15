package com.stuypulse.robot.subsystems.vision;

import com.stuypulse.robot.constants.Cameras;
import com.stuypulse.robot.constants.Cameras.Camera;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.util.vision.LimelightHelpers;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class LimelightVision extends SubsystemBase {
    public static final LimelightVision instance;
    
    static {
        instance = new LimelightVisionImpl();
    }

    public static LimelightVision getInstance() {
        return instance;
    }
    
    public enum MegaTagMode {
        MEGATAG1,
        MEGATAG2
    }

    public abstract Pose2d getEstimatedPose();

    public abstract void resetEstimatedPose(Pose2d newPose);
}
