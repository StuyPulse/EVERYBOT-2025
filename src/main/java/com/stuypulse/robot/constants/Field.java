package com.stuypulse.robot.constants;

import java.util.function.Supplier;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Field {
    public enum ReefTags {
        RED_KL(6),
        RED_AB(7),
        RED_CD(8),
        RED_EF(9),
        RED_GH(10),
        RED_IJ(11),

        BLUE_CD(17),
        BLUE_AB(18),
        BLUE_KL(19),
        BLUE_IJ(20),
        BLUE_GH(21),
        BLUE_EF(22);

        private final int id;

        private ReefTags(int id) {
            this.id = id;
        }

        public int getID() { 
            return this.id;
        }
    
        private static ReefTags intToReefTag(int id) {
            for(ReefTags i: ReefTags.values()) {
                if (i.getID() == id) {
                    return i;
                }
            }
            return null;
        }
    }
    
    // 2025 Field Reef AprilTag Layout
    private static AprilTag REEF_APRILTAGS_RED[] = {
        // RED
        new AprilTag(6,  new Pose3d(new Translation3d(Units.inchesToMeters(530.49), Units.inchesToMeters(130.17), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(300)))),
        new AprilTag(7,  new Pose3d(new Translation3d(Units.inchesToMeters(546.87), Units.inchesToMeters(158.50), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(0)))),
        new AprilTag(8,  new Pose3d(new Translation3d(Units.inchesToMeters(530.49), Units.inchesToMeters(186.83), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(60)))),
        new AprilTag(9,  new Pose3d(new Translation3d(Units.inchesToMeters(497.77), Units.inchesToMeters(186.83), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(120)))),
        new AprilTag(10,  new Pose3d(new Translation3d(Units.inchesToMeters(481.39), Units.inchesToMeters(158.50), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(180)))),
        new AprilTag(11,  new Pose3d(new Translation3d(Units.inchesToMeters(497.77), Units.inchesToMeters(130.17), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(240)))),
    };

    private static AprilTag REEF_APRILTAGS_BLUE[] = {
        // BLUE
        new AprilTag(17,  new Pose3d(new Translation3d(Units.inchesToMeters(160.39), Units.inchesToMeters(130.17), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(240)))),
        new AprilTag(18,  new Pose3d(new Translation3d(Units.inchesToMeters(144.0), Units.inchesToMeters(158.50), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(180)))),
        new AprilTag(19,  new Pose3d(new Translation3d(Units.inchesToMeters(160.39), Units.inchesToMeters(186.83), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(120)))),
        new AprilTag(20,  new Pose3d(new Translation3d(Units.inchesToMeters(193.10), Units.inchesToMeters(186.83), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(60)))),
        new AprilTag(21,  new Pose3d(new Translation3d(Units.inchesToMeters(209.49), Units.inchesToMeters(158.50), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(0)))),
        new AprilTag(22,  new Pose3d(new Translation3d(Units.inchesToMeters(193.10), Units.inchesToMeters(130.17), Units.inchesToMeters(12.13)), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(300)))),
    };

    public static ReefTags getClosestFace(Supplier<Pose2d> robotPose) {
        final Translation2d robotTranslation = new Translation2d(robotPose.get().getX(), robotPose.get().getY());
        AprilTag closest = null;
        Double lowestDistance = Double.MAX_VALUE;

        //ONLY LOOP THROUGH OWN REEF'S APRIL TAGS
        var alliance = DriverStation.getAlliance();
        boolean onRedAlliance = alliance.isPresent() ? alliance.get() == DriverStation.Alliance.Red : true;
        AprilTag REEF_APRILTAGS[] = onRedAlliance ? REEF_APRILTAGS_RED : REEF_APRILTAGS_BLUE;
        
        // Loop throught Reef AprilTag list
        for (AprilTag tag : REEF_APRILTAGS) {
            if (robotTranslation.getDistance(tag.pose.getTranslation().toTranslation2d()) < lowestDistance) {
                SmartDashboard.putNumber("Alignment/Translation Distance/Tag ID " + tag.ID, robotTranslation.getDistance(tag.pose.getTranslation().toTranslation2d()));
                closest = tag;
                lowestDistance = robotTranslation.getDistance(tag.pose.getTranslation().toTranslation2d());
            }
        }
        return ReefTags.intToReefTag(closest.ID);
    }
}
