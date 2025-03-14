package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

public class PivotAlgaeOutake {
    private Pivot pivot;    
    public PivotAlgaeOutake() {
        pivot = Pivot.getInstance();
    }
    public void initialize() {
        pivot.setRollerMotor(Settings.Pivot.ALGAE_SHOOT_SPEED.get());
    }
}
