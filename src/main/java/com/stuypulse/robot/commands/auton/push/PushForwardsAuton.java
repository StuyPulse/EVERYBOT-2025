package com.stuypulse.robot.commands.auton.push;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.constants.Settings.LEDPatterns;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Push alliance partner forwards for the Ranking Point
 * 
 * @author Sebastian Waldman
 */
public class PushForwardsAuton extends SequentialCommandGroup {
    public PushForwardsAuton() {
        addCommands(
            new LEDApplyPattern(LEDPatterns.PUSH_FORWARDS_AUTON),
            new DriveTank(1, 1, true)
                .withTimeout(7),
            new DriveTank(0,0,true)
                .withTimeout(0.1)
        );
    }
}
