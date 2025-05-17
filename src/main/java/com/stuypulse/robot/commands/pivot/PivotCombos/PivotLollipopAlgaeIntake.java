package com.stuypulse.robot.commands.pivot.PivotCombos;

import com.stuypulse.robot.commands.pivot.PivotToLollipopIntake;
import com.stuypulse.robot.commands.pivot.SetPivotControlMode;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeIntake;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PivotLollipopAlgaeIntake extends SequentialCommandGroup {
    public PivotLollipopAlgaeIntake() {
        addCommands(
            new SetPivotControlMode(Pivot.PivotControlMode.USING_STATES)
                .withTimeout(0.1),
            new PivotToLollipopIntake()
                .withTimeout(2),
            new PivotAlgaeIntake()
        );
    }
}
