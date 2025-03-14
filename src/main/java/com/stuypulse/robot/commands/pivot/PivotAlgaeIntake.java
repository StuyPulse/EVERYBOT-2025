package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

public class PivotAlgaeIntake {
    private Pivot pivot;    
    public PivotAlgaeIntake() {
        pivot = Pivot.getInstance();
    }
    public void initialize() {
        pivot.setRollerMotor(Settings.Pivot.ALGAE_INTAKE_SPEED.get());
    }
}
