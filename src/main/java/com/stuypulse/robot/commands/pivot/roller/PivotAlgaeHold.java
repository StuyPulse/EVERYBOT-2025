package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.util.RobotVisualizer;

import edu.wpi.first.wpilibj2.command.Command;

public class PivotAlgaeHold extends Command {
    private Pivot pivot;

    public PivotAlgaeHold() {
        pivot = Pivot.getInstance();
        addRequirements(pivot);
    }

    @Override
    public void execute() {
        RobotVisualizer.getInstance().updateRollers(Settings.Pivot.ALGAE_HOLD_SPEED.getAsDouble());
        pivot.setRollerMotor(Settings.Pivot.ALGAE_HOLD_SPEED.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
