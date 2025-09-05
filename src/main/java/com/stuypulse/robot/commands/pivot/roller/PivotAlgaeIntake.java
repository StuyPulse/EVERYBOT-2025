package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;

public class PivotAlgaeIntake extends PivotRollerToSpeed {
    public PivotAlgaeIntake() {
        super(Settings.Pivot.ALGAE_INTAKE_SPEED.getAsDouble());
    }
}
