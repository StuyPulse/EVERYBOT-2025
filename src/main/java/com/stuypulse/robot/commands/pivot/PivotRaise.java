package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;

public class PivotRaise extends PivotToDirection {
    public PivotRaise() {
        super(Settings.Pivot.PIVOT_RAISE_SPEED.getAsDouble());
    }
}
