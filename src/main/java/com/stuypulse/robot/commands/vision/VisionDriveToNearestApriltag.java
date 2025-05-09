package com.stuypulse.robot.commands.vision;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class VisionDriveToNearestApriltag extends InstantCommand {
    private final Drivetrain drive;

    public VisionDriveToNearestApriltag() {
        drive = Drivetrain.getInstance();
    }

    @Override
    public void initialize() {
        drive.driveToNearestAprilTag();
    }
}
