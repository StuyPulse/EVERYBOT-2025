package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotControlMode;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SetPivotControlMode extends InstantCommand {
    Pivot pivot = Pivot.getInstance();
    PivotControlMode pivotControlMode;

    public SetPivotControlMode(PivotControlMode pivotControlMode) {
        this.pivotControlMode = pivotControlMode;
    }

    @Override
    public void initialize() {
        pivot.setPivotControlMode(pivotControlMode);
    }
}
