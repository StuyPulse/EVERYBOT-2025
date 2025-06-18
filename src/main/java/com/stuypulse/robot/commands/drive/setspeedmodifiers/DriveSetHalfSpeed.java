package com.stuypulse.robot.commands.drive.setspeedmodifiers;

import com.stuypulse.robot.constants.Settings;

public class DriveSetHalfSpeed extends DriveSetSpeedModifier {
    public DriveSetHalfSpeed() {
        super(Settings.Drivetrain.DRIVE_HALF_SPEED_MODIFIER);
    }
}
