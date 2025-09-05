package com.stuypulse.robot.commands.pivot.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotToState extends InstantCommand {
    private Pivot pivot;
    private PivotState pivotState;

    public PivotToState(PivotState pivotState) {
        pivot = Pivot.getInstance();
        this.pivotState = pivotState;

        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        pivot.setPivotState(pivotState);
    }
}
