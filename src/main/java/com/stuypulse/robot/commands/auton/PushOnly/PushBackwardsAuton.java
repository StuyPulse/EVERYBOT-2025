package com.stuypulse.robot.commands.auton.PushOnly;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.constants.Settings.LEDPatterns;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class PushBackwardsAuton extends SequentialCommandGroup {
    public PushBackwardsAuton() {
        addCommands(
            new LEDApplyPattern(LEDPatterns.PUSH_BACKWARDS_AUTON),
            new DriveTank(-.75, -.75, true),
            new WaitUntilCommand(2.00),
            new DriveTank(.3, .3, true),
            new WaitUntilCommand(10),
            new DriveTank(0, 0, true)
        );
    }
}