package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotCoralOuttake extends PivotRollerToDirection {
    public PivotCoralOuttake() {
        super(Settings.Pivot.ROLLER_STOP_SPEED.getAsDouble());
    }
}
