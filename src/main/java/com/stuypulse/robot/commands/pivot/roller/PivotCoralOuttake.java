package com.stuypulse.robot.commands.pivot.roller;
import com.stuypulse.robot.constants.Settings;
public class PivotCoralOuttake extends PivotRollerToDirection {
    public PivotCoralOuttake() {
        super(Settings.Pivot.CORAL_SHOOT_SPEED.getAsDouble());
    }
}
