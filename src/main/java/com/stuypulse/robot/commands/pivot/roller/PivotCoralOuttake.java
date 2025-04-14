package com.stuypulse.robot.commands.pivot.roller;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/*public class PivotCoralOuttake extends SequentialCommandGroup {
    public PivotCoralOuttake() {
        addCommands(
            new LEDApplyPattern(Settings.LEDPatterns.CORAL_OUT),
            new PivotToDefault(),
            new PivotRollerToDirection(Settings.Pivot.CORAL_SHOOT_SPEED.getAsDouble()),
            new PivotToDirection(Settings.Pivot.PIVOT_LOWER_SPEED.getAsDouble()),
            new WaitUntilCommand(1),
            new PivotRollerToDirection(0),
            new PivotToCoralStow()
        );
    }
}*/

public class PivotCoralOuttake extends PivotRollerToDirection {
    public PivotCoralOuttake() {
        super(Settings.Pivot.CORAL_SHOOT_SPEED.getAsDouble());
    }
}
