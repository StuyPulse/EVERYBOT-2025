package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotStop extends InstantCommand {
    private Pivot pivot;

    public PivotStop() {
        pivot = Pivot.getInstance();
    }

    public void initialize() {
        pivot.setPivotMotor(0);
    }

}
