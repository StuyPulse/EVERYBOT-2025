package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;

public class PivotAlgaeOuttake extends PivotRollerToSpeed {
    public PivotAlgaeOuttake() {
        super(Settings.Pivot.ALGAE_OUTTAKE_SPEED.getAsDouble());
    }
}
