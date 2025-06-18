package com.stuypulse.robot.commands.drive.setSpeedModifiers;

import com.stuypulse.robot.constants.Settings;

public class DriveDisable extends DriveSetSpeedModifier{
    public DriveDisable() {
        super(Settings.Drivetrain.DRIVE_DISABLE_SPEED);
    }
}
