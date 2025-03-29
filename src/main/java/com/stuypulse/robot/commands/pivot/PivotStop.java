package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

public class PivotStop extends PivotToDirection {
    private Pivot pivot;

    public PivotStop() {
        super(Settings.Pivot.ROLLER_STOP_SPEED.getAsDouble());
    }

}
