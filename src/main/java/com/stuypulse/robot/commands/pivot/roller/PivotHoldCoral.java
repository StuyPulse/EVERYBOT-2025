package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.Command;

public class PivotHoldCoral extends Command {
    private Pivot pivot;

    public PivotHoldCoral() {
        pivot = Pivot.getInstance();
        addRequirements(pivot);
    }

    @Override
    public void execute() {
        pivot.setRollerMotor(Settings.Pivot.ROLLER_ROTISSERIE_SPEED.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
