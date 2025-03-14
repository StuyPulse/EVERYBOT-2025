package com.stuypulse.robot.commands.drive;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;


public class DriveTank extends Command {
    private final DoubleSupplier leftSpeed;
    private final DoubleSupplier rightSpeed;
    private final Drivetrain drive;
    private final BooleanSupplier squared;

    public DriveTank(Drivetrain driveSubsystem,
                     DoubleSupplier leftSpeed,
                     DoubleSupplier rightSpeed,
                     BooleanSupplier squared) {
        this.drive = driveSubsystem;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.squared = squared;

        addRequirements(this.drive);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        drive.driveTank(leftSpeed.getAsDouble(), rightSpeed.getAsDouble(), squared.getAsBoolean());
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}