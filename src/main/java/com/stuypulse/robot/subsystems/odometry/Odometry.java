package com.stuypulse.robot.subsystems.odometry;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Odometry extends SubsystemBase {
    public static final Odometry instance;
    
    static {
        instance = new OdometryImpl();
    }
    
    public static Odometry getInstance() {
        return instance;
    }
    
    public abstract void resetEstimatedPose(Pose2d newPose);
    public abstract void updateVisionMeasurement(Matrix<N3, N1> visionStdDevs, Pose2d pose, double timestampSeconds);

    public abstract Pose2d getEstimatedPose();

    @Override
    public void periodic() {}
}
