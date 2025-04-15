package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

public class PivotToCoralStow extends PivotToState {
    public PivotToCoralStow() {
        super(PivotState.STOW_CORAL);
    }
}
