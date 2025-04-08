package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;

public class PivotRaise extends PivotToDirection {

    public PivotRaise(double speedModifier) {
        super(speedModifier * Settings.Pivot.PIVOT_RAISE_SPEED.doubleValue());
    }
}
