package com.stuypulse.robot.commands.auton.coral;

import com.stuypulse.robot.commands.drive.DriveTank;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * attempt to score two coral
 * 
 * @author Sebastian Waldman
 */
public class DoubleCenterCoralAuton extends SequentialCommandGroup {
    public DoubleCenterCoralAuton() {
        addCommands(
            new DriveTank(-.45, -.45, true)
                .withTimeout(2.2),
            new DriveTank(0, 0, false)
                .withTimeout(0.1),
            // new PivotCoralScore()
                // .withTimeout(2.4),
            new DriveTank(.5,.5,true)
                .withTimeout(.5),
            new DriveTank(.7,.3,true)
                .withTimeout(.445),
            new DriveTank(-.7,-.7,true)
                .withTimeout(0.95),
            new DriveTank(-.7,-.3,true)
                .withTimeout(.65),
            new DriveTank(-.7,-.7,true)
                .withTimeout(1.2),
            new DriveTank(-.3,-.7,true) 
                .withTimeout(.535),
            new DriveTank(-.5,-.5,true)
                .withTimeout(1.65),
            new DriveTank(0,0,true)
                .withTimeout(1.2),
            new DriveTank(0.6,0.6,true)
                .withTimeout(0.4),
            new DriveTank(.5,-.7,true)
                .withTimeout(0.75),
            new DriveTank(-.6,-.6,true)
                .withTimeout(0.2)
            
        );
    }
}
