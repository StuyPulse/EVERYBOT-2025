package com.stuypulse.robot.commands.pivot;
import com.stuypulse.robot.constants.Settings;
public class PivotCoralOuttake extends PivotRollerToDirection {
    public PivotCoralOuttake() {
        super(Settings.Pivot.ROLLER_STOP_SPEED.getAsDouble());
    }
}
