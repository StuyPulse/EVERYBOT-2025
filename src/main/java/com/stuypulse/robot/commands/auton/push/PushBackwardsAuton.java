package com.stuypulse.robot.commands.auton.push;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.constants.Settings.LEDPatterns;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Push alliance partner backwards for the Ranking Point
 * 
 * @author Sebastian Waldman
 */
public class PushBackwardsAuton extends SequentialCommandGroup {
    public PushBackwardsAuton() {
        addCommands(
            new LEDApplyPattern(LEDPatterns.PUSH_BACKWARDS_AUTON),
            new DriveTank(.75, .75, true)
                .withTimeout(1),
            new DriveTank(-0.3, -0.3, true)
                .withTimeout(5),
            new DriveTank(0, 0, true)
                .withTimeout(0.1)
        );
    }
}
