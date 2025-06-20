package com.stuypulse.robot.commands.drive.setSpeedModifiers;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class DriveSetSpeedModifier extends InstantCommand{
    private double SpeedModifier;
    private final Drivetrain drivetrain = Drivetrain.getInstance();

    public DriveSetSpeedModifier(double targetSpeedModifier) {
        this.SpeedModifier = targetSpeedModifier;
    }

    @Override
    public void initialize() {
        drivetrain.setSpeedModifier(SpeedModifier);
    }
}
