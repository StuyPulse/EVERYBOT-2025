package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PivotReseatCoral extends SequentialCommandGroup{
    public PivotReseatCoral() {
        addCommands(
            new PivotToState(PivotState.RESEAT_CORAL)
                .alongWith(new SetPivotControlMode(Pivot.PivotControlMode.USING_STATES))
                .withTimeout(1),
            new PivotToCoralStow()
                .alongWith(new SetPivotControlMode(Pivot.PivotControlMode.USING_STATES))
                .withTimeout(1)
        );
    }
}
