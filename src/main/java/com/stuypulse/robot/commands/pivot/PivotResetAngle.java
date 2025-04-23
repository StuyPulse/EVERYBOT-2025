package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;
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
        pivot.resetPivotEncoder(Settings.Pivot.DEFAULT_ANGLE.getRotations());
        addRequirements(pivot);
    }
}