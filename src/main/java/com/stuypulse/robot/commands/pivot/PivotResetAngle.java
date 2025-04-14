package com.stuypulse.robot.commands.pivot;

import com.revrobotics.RelativeEncoder;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.PivotImpl;

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
    }
}
