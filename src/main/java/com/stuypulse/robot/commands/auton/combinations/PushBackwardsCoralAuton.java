package com.stuypulse.robot.commands.auton.combinations;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.PivotToDirection;
import com.stuypulse.robot.commands.pivot.roller.PivotCoralOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerStop;
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
            // new LEDApplyPattern(LEDPatterns.PUSH_BACKWARDS_SINGLE_L1_AUTON),
            new DriveTank(-.75, -.75, true)
                .withTimeout(2),
            new DriveTank(.3, .3, true)
                .withTimeout(10),
            new DriveTank(0, 0, true)
                .withTimeout(0.1),
            new PivotCoralOuttake()
                .withTimeout(3),
            new PivotRollerStop()
                .withTimeout(0.1)
        );
    }
}
