package com.stuypulse.robot.constants;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;

/** This interface stores information about each camera. */
public interface Cameras {

    public Camera[] LimelightCameras = new Camera[] {
        new Camera("limelight", new Pose3d(Units.inchesToMeters(0), Units.inchesToMeters(0), Units.inchesToMeters(0), new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(0), Units.degreesToRadians(0)))),  
    };

    public static class Camera {
        private String name;
        private Pose3d location;

        public Camera(String name, Pose3d location) {
            this.name = name;
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public Pose3d getLocation() {
            return location;
        }

    }
}