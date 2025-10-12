package com.stuypulse.robot.commands.pivot.pivotCombos;

import com.stuypulse.robot.commands.pivot.pivot.PivotToState;
import com.stuypulse.robot.commands.pivot.pivot.SetPivotControlMode;
import com.stuypulse.robot.commands.pivot.roller.PivotHoldCoral;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerReseat;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerToSpeed;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PivotCoralScoreAuto extends SequentialCommandGroup{
    public PivotCoralScoreAuto() {
        addCommands(
            new PivotCoralOuttake(),
            new SetPivotControlMode(Pivot.PivotControlMode.USING_STATES).withTimeout(0.01),
            new PivotToState(PivotState.SCORE_CORAL).withTimeout(0.1),
            new PivotHoldCoral().repeatedly().withTimeout(0.8),
            new WaitCommand(.5),
            new PivotToState(PivotState.DEFAULT).withTimeout(0.01)
        );
    }
}
