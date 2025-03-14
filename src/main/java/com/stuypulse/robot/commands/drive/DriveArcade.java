package com.stuypulse.robot.commands.drive;
import java.util.function.DoubleSupplier;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

public class DriveArcade extends Command {
    private final DoubleSupplier xSpeed;
    private final DoubleSupplier zRotation;
    private final BooleanSupplier squared;
    public final Drivetrain drive;

    public DriveArcade(Drivetrain driveSubsystem,
                     DoubleSupplier xSpeed,
                     DoubleSupplier zRotation,
                     BooleanSupplier squared) {
        this.drive = driveSubsystem;
        this.xSpeed = xSpeed;
        this.zRotation = zRotation;
        this.squared = squared;

        addRequirements(this.drive);
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        drive.driveArcade(xSpeed.getAsDouble(), zRotation.getAsDouble(), squared.getAsBoolean());
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}