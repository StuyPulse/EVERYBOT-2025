package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;
public class PivotAlgaeOuttake extends PivotToDirection {
    public PivotAlgaeOuttake() {
        super(Settings.Pivot.ALGAE_SHOOT_SPEED.getAsDouble());
    }
}
