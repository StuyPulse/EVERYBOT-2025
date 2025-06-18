package com.stuypulse.robot.commands.drive.setSpeedModifiers;
import com.stuypulse.robot.constants.Settings;

public class DriveSetFullSpeed extends DriveSetSpeedModifier{
    public DriveSetFullSpeed() {
        super(Settings.Drivetrain.DRIVE_FULL_SPEED_MODIFIER);
    }
}
