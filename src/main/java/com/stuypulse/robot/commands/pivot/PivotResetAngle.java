package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotResetAngle extends InstantCommand {
    private final Pivot pivot;
    
    public PivotResetAngle() {
        pivot = Pivot.getInstance();
        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        pivot.ResetPivotEncoder();
        addRequirements(pivot);
    }
}
