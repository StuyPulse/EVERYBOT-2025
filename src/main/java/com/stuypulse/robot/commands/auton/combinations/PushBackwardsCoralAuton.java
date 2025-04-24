package com.stuypulse.robot.commands.auton.combinations;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.PivotToDirection;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerToDirection;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.LEDPatterns;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * Push alliance partner's robot backwards then score a coral
 * 
 * @author Sebastian Waldman
 */
public class PushBackwardsCoralAuton extends SequentialCommandGroup {
    public PushBackwardsCoralAuton() {
        addCommands(
            new LEDApplyPattern(LEDPatterns.PUSH_BACKWARDS_SINGLE_L1_AUTON),
            new DriveTank(-.75, -.75, true),
            new WaitUntilCommand(2.00),
            new DriveTank(.3, .3, true),
            new WaitUntilCommand(10),
            new DriveTank(0, 0, true),
            new PivotRollerToDirection(Settings.Pivot.CORAL_SHOOT_SPEED.getAsDouble()),
            new PivotToDirection(Settings.Pivot.PIVOT_LOWER_SPEED.getAsDouble()),
            new WaitUntilCommand(1),
            new PivotRollerToDirection(0),
            new PivotToDirection(Settings.Pivot.PIVOT_RAISE_SPEED.getAsDouble())
        );
    }
}
