package com.stuypulse.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

public class DriveDefault extends Command {
    private final CommandXboxController gamepad;
    private final boolean squared;
    private final Drivetrain drivetrain;

    public DriveDefault(CommandXboxController gamepad,
                        boolean squared) {
        drivetrain = Drivetrain.getInstance();

        this.gamepad = gamepad;
        this.squared = squared;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        if(Settings.DEBUG_MODE) {
        SmartDashboard.putNumber("Drivetrain/Forward & Back", -gamepad.getLeftY());
        SmartDashboard.putNumber("Drivetrain/Rotation", gamepad.getRightX());
        }

        if(!Settings.EnabledSubsystems.DRIVETRAIN.get()) return;

        Drivetrain.getInstance().driveArcade(drivetrain.velocityFFCalculate(gamepad.getLeftY()).get()*drivetrain.getSpeedModifier(),  gamepad.getRightX()*drivetrain.getSpeedModifier(), squared); 
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
