package com.stuypulse.robot.commands.pivot.roller;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.PivotToDefault;
import com.stuypulse.robot.commands.pivot.PivotToDirection;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class PivotCoralOuttake extends SequentialCommandGroup {
    public PivotCoralOuttake() {
        addCommands(
            new PivotToDefault()
                .withTimeout(1),
            new PivotRollerToDirection(Settings.Pivot.CORAL_SHOOT_SPEED.getAsDouble())
                .withTimeout(1),
            new PivotRollerStop()
        );
    }
}