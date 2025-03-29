package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotAlgaeIntake extends PivotRollerToDirection {
    public PivotAlgaeIntake() {
        super(Settings.Pivot.ALGAE_INTAKE_SPEED.getAsDouble());
    }

}
