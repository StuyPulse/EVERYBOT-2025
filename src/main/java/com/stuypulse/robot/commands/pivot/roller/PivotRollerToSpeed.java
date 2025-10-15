package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotRollerToSpeed extends InstantCommand{
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
        if(Settings.DEBUG_MODE) SmartDashboard.putNumber("Pivot/Rollers/Speed", pivotRollerSpeed);
    }
}
