package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;

public class PivotRollerReseat extends PivotRollerToSpeed {
    public PivotRollerReseat() {
        super(Settings.Pivot.ROLLER_RESEAT_SPEED.getAsDouble());
    }
}
