package com.stuypulse.robot.commands.pivot.PivotCombos;

import com.stuypulse.robot.commands.pivot.PivotToLoliPopIntake;
import com.stuypulse.robot.commands.pivot.SetPivotControlMode;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeIntake;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PivotLolipopAlgeaIntake extends SequentialCommandGroup {
    public PivotLolipopAlgeaIntake() {
        addCommands(
            new SetPivotControlMode(Pivot.PivotControlMode.USING_STATES)
                .withTimeout(0.1),
            new PivotToLoliPopIntake()
                .withTimeout(2),
            new PivotAlgaeIntake()
        );
    }
}
