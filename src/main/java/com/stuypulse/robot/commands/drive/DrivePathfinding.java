package com.stuypulse.robot.commands.drive;

import com.pathplanner.lib.path.PathConstraints;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;

public class DrivePathfinding extends Command {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private DrivePathfinding(Pose2d targetPose, PathConstraints constraints, double endSpeed) {
        
    } 
    
}
