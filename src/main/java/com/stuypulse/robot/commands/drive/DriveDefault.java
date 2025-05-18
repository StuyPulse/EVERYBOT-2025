package com.stuypulse.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.stuylib.input.Gamepad;

public class DriveDefault extends Command {
    private final Gamepad gamepad;
    private final boolean squared;

    public DriveDefault(Gamepad gamepad,
                        boolean squared) {
        this.gamepad = gamepad;
        this.squared = squared;

        addRequirements(Drivetrain.getInstance());
    }

    @Override
    public void execute() {
        Drivetrain.getInstance().driveArcade(MathUtil.applyDeadband(-gamepad.getLeftStick().y, 0.06), gamepad.getRightStick().x, squared);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
