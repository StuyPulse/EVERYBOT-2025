package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.util.RobotVisualizer;

import edu.wpi.first.wpilibj2.command.Command;

public class UpdateRollerSpeedInVis extends Command{
    public UpdateRollerSpeedInVis(double pivotRollerSpeed) {
        RobotVisualizer.getInstance().updateRollers(pivotRollerSpeed);
    }
}
