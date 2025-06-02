package com.stuypulse.robot.commands.pivot.pivotCombos;

import com.stuypulse.robot.commands.pivot.PivotToLoliPopIntake;
import com.stuypulse.robot.commands.pivot.SetPivotControlMode;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeIntake;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PivotLollipopAlgaeIntake extends SequentialCommandGroup {
    public PivotLollipopAlgaeIntake() {
        addCommands(
            new PivotToLoliPopIntake()
                .withTimeout(2),
            new PivotAlgaeIntake()
        );
    }
}
