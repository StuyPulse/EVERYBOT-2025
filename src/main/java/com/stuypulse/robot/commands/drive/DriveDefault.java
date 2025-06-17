package com.stuypulse.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.stuylib.input.Gamepad;

public class DriveDefault extends Command {
    private final Gamepad gamepad;
    private final boolean squared;
    private final Drivetrain drivetrain;

    public DriveDefault(Gamepad gamepad,
                        boolean squared) {
        drivetrain = Drivetrain.getInstance();

        this.gamepad = gamepad;
        this.squared = squared;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        if(Settings.DEBUG_MODE) {
        SmartDashboard.putNumber("Drivetrain/Forward & Back", -gamepad.getLeftStick().y);
        SmartDashboard.putNumber("Drivetrain/Rotation", gamepad.getRightStick().x);
        }

        if(!Settings.EnabledSubsystems.DRIVETRAIN.get()) return;

        Drivetrain.getInstance().driveArcade(-gamepad.getLeftStick().y, gamepad.getRightStick().x, squared);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
