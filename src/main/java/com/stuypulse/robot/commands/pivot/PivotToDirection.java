package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.Command;

public class PivotToDirection extends Command {
    private Pivot pivot; 
    private double pivotMotorSpeed;
    public PivotToDirection(double d) {
        pivot = Pivot.getInstance();
       addRequirements(pivot); 
    }

    public void initialize() {
        pivot.setPivotMotor(pivotMotorSpeed);
    }
}
