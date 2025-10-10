package com.stuypulse.robot.commands.pivot.pivotCombos;
import com.stuypulse.robot.commands.pivot.pivot.PivotToDefault;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerStop;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerToSpeed;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class PivotCoralOuttake extends SequentialCommandGroup {
    public PivotCoralOuttake() {
        addCommands(
            new PivotToDefault(),
            new PivotRollerToSpeed(Settings.Pivot.CORAL_SHOOT_SPEED.getAsDouble()),
            new WaitCommand(.32).withTimeout(.32),
            new PivotRollerStop()
        );
    }
}