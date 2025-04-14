package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotToState extends InstantCommand {
    public PivotToState(PivotState pivotState) {
        super(() -> Pivot.getInstance().setPivotState(pivotState));
        addRequirements(Pivot.getInstance());
    }
}
