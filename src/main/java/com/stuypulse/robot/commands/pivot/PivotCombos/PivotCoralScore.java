package com.stuypulse.robot.commands.pivot.PivotCombos;

import com.stuypulse.robot.commands.pivot.PivotToCoralStow;
import com.stuypulse.robot.commands.pivot.PivotToState;
import com.stuypulse.robot.commands.pivot.SetPivotControlMode;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerStop;
import com.stuypulse.robot.commands.pivot.roller.PivotCoralOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotHoldCoral;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotState;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PivotCoralScore extends SequentialCommandGroup{
    public PivotCoralScore() {
        addCommands(
						new PivotCoralOuttake().withTimeout(.85),
                        new PivotToState(PivotState.SCORE_CORAL).withTimeout(.02),
                        new PivotToCoralStow().withTimeout(.5),
                        new PivotRollerStop().withTimeout(.02));
    }
}
