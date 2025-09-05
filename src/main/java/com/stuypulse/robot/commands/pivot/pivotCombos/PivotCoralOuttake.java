package com.stuypulse.robot.commands.pivot.pivotCombos;
import com.stuypulse.robot.commands.pivot.pivot.PivotToDefault;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerStop;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerToSpeed;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PivotCoralOuttake extends SequentialCommandGroup {
    public PivotCoralOuttake() {
        addCommands(
            new PivotToDefault()
                .withTimeout(1),
            new PivotRollerToSpeed(Settings.Pivot.CORAL_SHOOT_SPEED.getAsDouble())
                .withTimeout(1),
            new PivotRollerStop()
        );
    }
}