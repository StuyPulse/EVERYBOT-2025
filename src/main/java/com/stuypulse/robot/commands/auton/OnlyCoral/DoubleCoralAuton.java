package com.stuypulse.robot.commands.auton.OnlyCoral;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.LEDPatterns;
import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.PivotToDirection;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerToDirection;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * @author Sebastian Waldman
 */
public class DoubleCoralAuton extends SequentialCommandGroup {
    public DoubleCoralAuton() {
        addCommands(
            new LEDApplyPattern(LEDPatterns.TWO_L1_AUTON),
            new DriveTank(.5,.5,true),
            new WaitUntilCommand(2),
            new DriveTank(0,0,true),
            new PivotRollerToDirection(Settings.Pivot.CORAL_SHOOT_SPEED.getAsDouble()),
            new PivotToDirection(Settings.Pivot.PIVOT_LOWER_SPEED.getAsDouble()),
            new WaitUntilCommand(1),
            new PivotRollerToDirection(0),
            new PivotToDirection(Settings.Pivot.PIVOT_RAISE_SPEED.getAsDouble()),
            new DriveTank(-.5,-.5,true),
            new WaitUntilCommand(1),
            new DriveTank(.4,.5,true),
            new WaitUntilCommand(1),
            new DriveTank(.5,.4,true),
            new WaitUntilCommand(1),
            new DriveTank(0,0,true),
            new WaitUntilCommand(5),
            new DriveTank(-.5,-.5,true),
            new DriveTank(1, -1, true),
            new WaitUntilCommand(.7),
            new DriveTank(.5,.5,true),
            new WaitUntilCommand(2),
            new DriveTank(0,0,true),
            new PivotRollerToDirection(Settings.Pivot.CORAL_SHOOT_SPEED.getAsDouble()),
            new PivotToDirection(Settings.Pivot.PIVOT_LOWER_SPEED.getAsDouble()),
            new WaitUntilCommand(1),
            new PivotRollerToDirection(0),
            new PivotToDirection(Settings.Pivot.PIVOT_RAISE_SPEED.getAsDouble())
        );
    }
}
