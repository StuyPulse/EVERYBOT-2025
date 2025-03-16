package com.stuypulse.robot.commands.drive;
import edu.wpi.first.wpilibj2.command.Command;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;


public class DriveTank extends Command {
    private final double leftSpeed;
    private final double rightSpeed;
    private final boolean squared;

    public DriveTank(double leftSpeed, double rightSpeed, Boolean squared) {
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.squared = squared;

        addRequirements(Drivetrain.getInstance());
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Drivetrain.getInstance().driveTank(leftSpeed, rightSpeed, squared);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}