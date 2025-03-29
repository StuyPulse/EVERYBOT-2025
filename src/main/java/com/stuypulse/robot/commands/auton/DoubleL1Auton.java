package com.stuypulse.robot.commands.auton;

import com.stuypulse.robot.constants.Settings.LEDPatterns;
import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.PivotCoralOuttake;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/*-
 * @author sebastian waldman
 */
public class DoubleL1Auton extends SequentialCommandGroup {

    public DoubleL1Auton() {
        addCommands(
            new LEDApplyPattern(LEDPatterns.TWO_L1_AUTON),
            new DriveTank(.5,.5,true),
            new WaitUntilCommand(2),
            new DriveTank(0,0,true),
            new PivotCoralOuttake(),
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
            new PivotCoralOuttake()
            );
    }
}