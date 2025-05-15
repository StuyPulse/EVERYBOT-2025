package com.stuypulse.robot.subsystems.vision;

import com.stuypulse.robot.constants.Cameras;
import com.stuypulse.robot.constants.Cameras.Camera;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.util.vision.LimelightHelpers;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightVisionImpl extends LimelightVision {
    private final int maxTagCount = 2;
    private final MegaTagMode megaTagMode = MegaTagMode.MEGATAG1;

    private boolean doRejectUpdate = false;
    private boolean apriltagDetected = false;

    private final Matrix<N3, N1> visionStdDevs;
    private final DifferentialDrivePoseEstimator poseEstimator;
    private Pose2d limelightPose;
    
    private final Field2d field = new Field2d();

    private final Drivetrain drivetrain;

    LimelightVisionImpl() {
        visionStdDevs = VecBuilder.fill(.1, .1, .1);

        for (Camera camera : Cameras.LimelightCameras) {
            Pose3d robotRelativePose = camera.getLocation();
            LimelightHelpers.setCameraPose_RobotSpace(
                    camera.getName(),
                    robotRelativePose.getX(),
                    -robotRelativePose.getY(),
                    robotRelativePose.getZ(),
                    Units.radiansToDegrees(robotRelativePose.getRotation().getX()),
                    Units.radiansToDegrees(robotRelativePose.getRotation().getY()),
                    Units.radiansToDegrees(robotRelativePose.getRotation().getZ()));
        }

        drivetrain = Drivetrain.getInstance();
        poseEstimator = new DifferentialDrivePoseEstimator(drivetrain.getKinematics(), 
            drivetrain.getHeading(),
            drivetrain.getLeftDistance(),
            drivetrain.getRightDistance(),
            drivetrain.getPose());
    }
     
    private void updatePoseEstimator() {
        apriltagDetected = false;
        doRejectUpdate = false;

        poseEstimator.update(drivetrain.getHeading(), 
            Units.metersToInches(drivetrain.getLeftDistance()), 
            Units.metersToInches(drivetrain.getRightDistance()));

        if (megaTagMode == MegaTagMode.MEGATAG1) {
            LimelightHelpers.PoseEstimate mt1 = LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight");

            if (mt1.tagCount == 1 && mt1.rawFiducials.length == 1) {
                if(mt1.rawFiducials[0].ambiguity > .7) {
                    doRejectUpdate = true;
                }
                if(mt1.rawFiducials[0].distToCamera > 3) {
                    doRejectUpdate = true;
                }
            }

            if (mt1.tagCount == 0) {
                doRejectUpdate = true;
            }

            if(!doRejectUpdate) {
                apriltagDetected = true;

                poseEstimator.setVisionMeasurementStdDevs(visionStdDevs);
                poseEstimator.addVisionMeasurement(
                    mt1.pose,
                    mt1.timestampSeconds);
            }

        } else if (megaTagMode == MegaTagMode.MEGATAG2) {
            LimelightHelpers.SetRobotOrientation("limelight", poseEstimator.getEstimatedPosition().getRotation().getDegrees(), 0,0, 0, 0,0);
            LimelightHelpers.PoseEstimate mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
            
            if(Math.abs(Drivetrain.getInstance().getGyroRate()) > 360) {
                doRejectUpdate = true;
            }

            if(mt2.tagCount == 0) {
                doRejectUpdate = true;
            }

            if(!doRejectUpdate) {
                apriltagDetected = true;

                poseEstimator.setVisionMeasurementStdDevs(visionStdDevs);
                poseEstimator.addVisionMeasurement(
                    mt2.pose,
                    mt2.timestampSeconds
                );             
            }
        }
    }

    public Pose2d getEstimatedPose() {
        updatePoseEstimator();
        limelightPose = poseEstimator.getEstimatedPosition();
        return limelightPose;
    }

    public void resetEstimatedPose(Pose2d newPose) {
        poseEstimator.resetPose(newPose);
        getEstimatedPose();
    }

    @Override
    public void periodic() {
        getEstimatedPose();
        field.setRobotPose(limelightPose);

        SmartDashboard.putData("Field", field);
        SmartDashboard.putBoolean("Vision/AprilTag Detected?", apriltagDetected);
     }
}
