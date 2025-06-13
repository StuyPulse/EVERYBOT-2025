package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.commands.pivot.PivotToDirection;
import com.stuypulse.robot.constants.Settings;

public class PivotHoldCoral extends PivotRollerToSpeed {
    public PivotHoldCoral() {
        super(Settings.Pivot.ROLLER_ROTISSERIE_SPEED.getAsDouble());
    }
}
