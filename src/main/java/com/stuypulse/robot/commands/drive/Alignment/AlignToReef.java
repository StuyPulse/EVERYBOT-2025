package com.stuypulse.robot.commands.drive.Alignment;

import edu.wpi.first.wpilibj2.command.Command;

import com.pathplanner.lib.path.PathPlannerPath;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

public class AlignToReef extends Command{
    private final PathPlannerPath path;
    public Drivetrain drivetrain = Drivetrain.getInstance();

    public AlignToReef (PathPlannerPath path) {
        this.path = path;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        if(path != null) drivetrain.pathfindThenFollowPath(Settings.Drivetrain.REEF_ALIGNMENT_CONSTRAINTS, this.path);
    }
}