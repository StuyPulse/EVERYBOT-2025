package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;

public class PivotRollerStop extends PivotRollerToSpeed {
    public PivotRollerStop() {
        super(Settings.Pivot.ROLLER_STOP_SPEED.getAsDouble());
    }
}
