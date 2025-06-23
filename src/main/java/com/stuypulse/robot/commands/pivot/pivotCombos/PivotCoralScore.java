package com.stuypulse.robot.commands.pivot.pivotCombos;

import com.stuypulse.robot.commands.pivot.pivot.PivotToState;
import com.stuypulse.robot.commands.pivot.pivot.SetPivotControlMode;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PivotCoralScore extends SequentialCommandGroup{
    public PivotCoralScore() {
        addCommands(
            new PivotCoralOuttake()
                .withTimeout(0.25),
            new PivotToState(PivotState.SCORE_CORAL)
                .alongWith(new SetPivotControlMode(Pivot.PivotControlMode.USING_STATES))
                .withTimeout(2)
        );
    }
}
