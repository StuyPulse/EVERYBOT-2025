package com.stuypulse.robot.commands.auton.combinations;

import com.stuypulse.robot.commands.drive.DriveTank;
import com.stuypulse.robot.commands.pivot.pivotCombos.PivotCoralScore;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * attempt to score two coral
 * 
 * @author Sebastian Waldman
 */
public class CoralgaeAuton extends SequentialCommandGroup {
    public CoralgaeAuton() {
        addCommands(
            new DriveTank(-.45, -.45, true)
                .withTimeout(2.2),
            new DriveTank(0, 0, true)
                .withTimeout(0.1),
            new PivotCoralScore()
                .withTimeout(2.0),
            new DriveTank(.5,.5,true)
                .withTimeout(.5),
            new DriveTank(.7,.3,true)
                .withTimeout(.45),
            new DriveTank(-.7,-.7,true)
                .withTimeout(1.15),
            new DriveTank(-.7,-.3,true)
                .withTimeout(.5)
            
        );
    }
}
