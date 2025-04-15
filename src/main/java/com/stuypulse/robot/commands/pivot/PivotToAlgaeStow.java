package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

public class PivotToAlgaeStow extends PivotToState {
    public PivotToAlgaeStow() {
        super(PivotState.STOW_ALGAE);
    }
}
