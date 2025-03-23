package com.stuypulse.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.Command;

import com.stuypulse.robot.Stick;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

public class DriveJoystick extends Command {
    private final Stick joystick;
    private final boolean squared;

    public DriveJoystick(Stick joystick,
                        boolean squared) {
        this.joystick = joystick;
        this.squared = squared;

        addRequirements(Drivetrain.getInstance());
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Drivetrain.getInstance().driveArcade(joystick.getY(), joystick.getX(), squared);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}