package com.stuypulse.robot.commands.pivot.pivotCombos;

import com.stuypulse.robot.commands.pivot.pivot.PivotToLollipopIntake;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeIntake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PivotLollipopAlgaeIntake extends SequentialCommandGroup {
    public PivotLollipopAlgaeIntake() {
        addCommands(
            new PivotToLollipopIntake()
                .withTimeout(2),
            new PivotAlgaeIntake()
        );
    }
}
