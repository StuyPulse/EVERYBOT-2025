package com.stuypulse.robot.commands.pivot.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

public class PivotToAlgaeIntake extends PivotToState {
    public PivotToAlgaeIntake() {
        super(PivotState.INTAKE_ALGAE);
    }
}
