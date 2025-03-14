package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotRaise extends InstantCommand {
    private Pivot pivot;

    public PivotRaise() {
        pivot = Pivot.getInstance();
    }

    public void initialize() {
        pivot.setPivotMotor(Settings.Pivot.PIVOT_RAISE_SPEED.get());
    }

}
