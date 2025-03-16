package com.stuypulse.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.Command;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

public class DriveArcade extends Command {
    private final double xSpeed;
    private final double zRotation;
    private final boolean squared;

    public DriveArcade(double xSpeed,
                        double zRotation,
                        boolean squared) {
        this.xSpeed = xSpeed;
        this.zRotation = zRotation;
        this.squared = squared;

        addRequirements(Drivetrain.getInstance());
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Drivetrain.getInstance().driveArcade(xSpeed, zRotation, squared);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}