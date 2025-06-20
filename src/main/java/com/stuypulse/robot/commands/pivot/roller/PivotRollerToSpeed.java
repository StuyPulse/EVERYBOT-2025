package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.Command;

public class PivotRollerToSpeed extends Command{
    private Pivot pivot;
    private double pivotRollerSpeed;

    public PivotRollerToSpeed(double pivotRollerSpeed) {
        pivot = Pivot.getInstance();
        this.pivotRollerSpeed = pivotRollerSpeed;

        addRequirements(pivot);
    }

    public void initialize() {
        if(!Settings.EnabledSubsystems.PIVOT_ROLLERS.get()) return;
        
        pivot.setRollerMotor(pivotRollerSpeed);
    }
}
