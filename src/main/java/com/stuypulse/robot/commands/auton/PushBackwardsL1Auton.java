package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.PivotCoralOut;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class PushBackwardsL1Auton extends SequentialCommandGroup {
    public PushBackwardsL1Auton() {
        addCommands(
            new LEDApplyPattern(LEDPattern.solid(Color.kSeaGreen).blink(Units.Seconds.of(1.5))),
            new DriveTank(Drivetrain.getInstance(), -.75, -.75, true),
            new WaitUntilCommand(2.00),
            new DriveTank(Drivetrain.getInstance(), .3, .3, true),
            new WaitUntilCommand(10),
            new DriveTank(Drivetrain.getInstance(), 0, 0, true),
            new PivotCoralOut()
        );
    }
}