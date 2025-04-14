package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;

public class PivotStop extends PivotToDirection {
    public PivotStop() {
        super(Settings.Pivot.ROLLER_STOP_SPEED.getAsDouble());
    }
}
