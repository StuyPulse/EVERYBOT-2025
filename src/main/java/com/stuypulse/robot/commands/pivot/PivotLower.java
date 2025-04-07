package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;

public class PivotLower extends PivotToDirection {

    public PivotLower() {

        super(Settings.Pivot.PIVOT_LOWER_SPEED.doubleValue());
    }
}
