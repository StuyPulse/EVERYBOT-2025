package com.stuypulse.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.stuylib.input.Gamepad;

public class DriveDefault extends Command {
    private final Gamepad gamepad;
    private final boolean squared;
    private double xInput = 0;

    public DriveDefault(Gamepad gamepad,
                        boolean squared) {
        this.gamepad = gamepad;
        this.squared = squared;
        
        addRequirements(Drivetrain.getInstance());
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Drivetrain/ Left trigger", gamepad.getLeftTrigger());
        SmartDashboard.putNumber("Drivetrain/ Right trigger", gamepad.getRightTrigger());
        SmartDashboard.putNumber("Drivetrain/ xinput", xInput);
        this.xInput = gamepad.getLeftTrigger() - gamepad.getRightTrigger();

        if(!Settings.EnabledSubsystems.DRIVETRAIN.get()) return;

        Drivetrain.getInstance().driveArcade(xInput, gamepad.getRightStick().x, squared);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
