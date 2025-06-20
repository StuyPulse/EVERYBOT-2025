package com.stuypulse.robot.commands.pivot.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot.PivotControlMode;

public class PivotSetControlUsingStates extends SetPivotControlMode {
    public PivotSetControlUsingStates() {
        super(PivotControlMode.USING_STATES);
    }
}
