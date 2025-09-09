package com.stuypulse.robot.subsystems.odometry;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OdometryImpl extends Odometry {
    private final DifferentialDrivePoseEstimator poseEstimator;
    private final Drivetrain drivetrain;

    private final Field2d field = new Field2d();

    public OdometryImpl() {
        drivetrain = Drivetrain.getInstance();

        poseEstimator = new DifferentialDrivePoseEstimator(drivetrain.getKinematics(),
                drivetrain.getHeading(),
                drivetrain.getLeftDistance(),
                drivetrain.getRightDistance(),
                drivetrain.getPose());
    }

    private void updateDrivetrainMeasurement() {
        poseEstimator.update(drivetrain.getHeading(),
                drivetrain.getLeftDistance(),
                drivetrain.getRightDistance());
    }

    @Override
    public void updateVisionMeasurement(Matrix<N3, N1> visionStdDevs, Pose2d pose, double timestampSeconds) {
        poseEstimator.setVisionMeasurementStdDevs(visionStdDevs);
        poseEstimator.addVisionMeasurement(
                pose,
                timestampSeconds);
    }

    @Override
    public void resetEstimatedPose(Pose2d newPose) {
        poseEstimator.resetPose(newPose);
    }

    @Override
    public Pose2d getEstimatedPose() {
        return poseEstimator.getEstimatedPosition();
    }

    @Override
    public void periodic() {
        super.periodic();

        updateDrivetrainMeasurement();

        field.setRobotPose(getEstimatedPose());

        SmartDashboard.putData("Field", field);
    }
}
