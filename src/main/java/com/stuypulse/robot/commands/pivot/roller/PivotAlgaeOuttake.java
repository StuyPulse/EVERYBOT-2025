package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;
public class PivotAlgaeOuttake extends PivotRollerToDirection {
    public PivotAlgaeOuttake() {
        super(Settings.Pivot.ALGAE_SHOOT_SPEED.getAsDouble());
    }
}
