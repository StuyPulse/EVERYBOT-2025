package com.stuypulse.robot.commands.drive;
import java.util.function.DoubleSupplier;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.stuylib.input.Gamepad;

public class DriveDefault extends Command {
    private final Gamepad gamepad;
    private final boolean squared;
    public final Drivetrain drive;

    public DriveDefault(Drivetrain driveSubsystem,
                     Gamepad gamepad,
                     boolean squared) {
        this.drive = driveSubsystem;
        this.gamepad = gamepad;
        this.squared = squared;

        addRequirements(this.drive);
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        drive.driveArcade(gamepad.getLeftStick().y, gamepad.getRightStick().x, squared);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}