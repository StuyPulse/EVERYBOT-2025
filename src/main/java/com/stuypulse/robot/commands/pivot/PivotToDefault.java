package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

public class PivotToDefault extends PivotToState {
    public PivotToDefault() {
        super(PivotState.DEFAULT);
    }
}