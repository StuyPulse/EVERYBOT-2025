package com.stuypulse.robot.commands.auton.coral;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.LEDPatterns;
import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.pivot.PivotToDirection;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerToDirection;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * attempt to score two coral
 * 
 * @author Sebastian Waldman
 */
public class DoubleCoralAuton extends SequentialCommandGroup {
    public DoubleCoralAuton() {
        addCommands(
            // previous code will not work guarenteed, wait until odometry is complete
        );
    }
}
