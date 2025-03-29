package com.stuypulse.robot.commands.pivot;
import com.stuypulse.robot.constants.Settings;

public class PivotAlgaeIntake extends PivotRollerToDirection {
    public PivotAlgaeIntake() {
        super(Settings.Pivot.ALGAE_INTAKE_SPEED.getAsDouble());
    }

}
