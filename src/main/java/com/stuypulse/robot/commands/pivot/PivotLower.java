package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotLower extends InstantCommand {
    private Pivot pivot;

    public PivotLower() {
        pivot = Pivot.getInstance();
        addRequirements(pivot);
    }

    public void initialize() {
        pivot.setPivotMotor(Settings.Pivot.PIVOT_LOWER_SPEED.get());
    }

}
