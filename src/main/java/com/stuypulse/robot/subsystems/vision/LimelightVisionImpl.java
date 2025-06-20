package com.stuypulse.robot.subsystems.vision;

import com.stuypulse.robot.constants.Cameras;
import com.stuypulse.robot.constants.Cameras.Camera;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.odometry.Odometry;
import com.stuypulse.robot.util.vision.LimelightHelpers;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightVisionImpl extends LimelightVision {
    private MegaTagMode megaTagMode = MegaTagMode.MEGATAG1;
    private Odometry odometry;

    private boolean doRejectUpdate = false;
    private boolean apriltagDetected = false;

    private final Matrix<N3, N1> visionStdDevs; //X, Y, Theta

    LimelightVisionImpl() {
        odometry = Odometry.getInstance();

        visionStdDevs = VecBuilder.fill(1, 1, .3);

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
    }

    public void SetMegaTagMode(MegaTagMode mtMode) {
        this.megaTagMode = mtMode;
    }
     
    private void updatePoseEstimatorVisionMeasurement() {
        apriltagDetected = false;
        doRejectUpdate = false;

        if(!Settings.EnabledSubsystems.VISION.get()) return;

        if (megaTagMode == MegaTagMode.MEGATAG1) {
            LimelightHelpers.PoseEstimate mt1 = LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight"); 

            if(mt1 == null) return;

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

                odometry.updateVisionMeasurement(visionStdDevs, mt1.pose, mt1.timestampSeconds);
            }

        } else if (megaTagMode == MegaTagMode.MEGATAG2) {
            LimelightHelpers.SetRobotOrientation("limelight", odometry.getEstimatedPose().getRotation().getDegrees(), 0,0, 0, 0,0);
            LimelightHelpers.PoseEstimate mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
            
            if(Math.abs(Drivetrain.getInstance().getGyroRate()) > 360) {
                doRejectUpdate = true;
            }

            if(mt2.tagCount == 0) {
                doRejectUpdate = true;
            }

            if(!doRejectUpdate) {
                apriltagDetected = true;

                odometry.updateVisionMeasurement(visionStdDevs, mt2.pose, mt2.timestampSeconds);        
            }
        }
    }

    @Override
    public void periodic() {
        updatePoseEstimatorVisionMeasurement();

        SmartDashboard.putBoolean("Vision/AprilTag Detected?", apriltagDetected);
        SmartDashboard.putString("Vision/Megatag Version", megaTagMode.toString());
     }
}
