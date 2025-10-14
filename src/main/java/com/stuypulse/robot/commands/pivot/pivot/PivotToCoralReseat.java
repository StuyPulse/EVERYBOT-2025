package com.stuypulse.robot.commands.pivot.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

public class PivotToCoralReseat extends PivotToState {
    public PivotToCoralReseat() {
        super(PivotState.RESEAT_CORAL);
    }
}
