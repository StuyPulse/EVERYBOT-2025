package com.stuypulse.robot.commands.pivot.PivotCombos;

import com.stuypulse.robot.commands.pivot.PivotToDirection;
import com.stuypulse.robot.commands.pivot.roller.PivotCoralOuttake;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PivotCoralScore extends SequentialCommandGroup{
    public PivotCoralScore() {
        addCommands(
            new PivotCoralOuttake()
                .withTimeout(0.5),
            new PivotToDirection(Settings.Pivot.PIVOT_SCORE_LOWER_SPEED.getAsDouble())
        );
    }
    
}
