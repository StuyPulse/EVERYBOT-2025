package com.stuypulse.robot.commands.pivot.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot.PivotControlMode;

public class PivotSetControlManual extends SetPivotControlMode {
    public PivotSetControlManual() {
        super(PivotControlMode.MANUAL);
    }
}
