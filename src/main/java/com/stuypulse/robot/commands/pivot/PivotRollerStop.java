package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;

public class PivotRollerStop extends PivotRollerToDirection {
    public PivotRollerStop() {
        super(Settings.Pivot.ROLLER_STOP_SPEED.getAsDouble());
    }
}
