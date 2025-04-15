package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.Command;

public class PivotToDirection extends Command {
    private Pivot pivot; 
    private double pivotMotorSpeed;
  
    public PivotToDirection(double pivotMotorSpeed) {
        pivot = Pivot.getInstance();
        this.pivotMotorSpeed = pivotMotorSpeed;
        
        addRequirements(pivot); 
    }

    public void initialize() {
        pivot.setPivotMotor(pivotMotorSpeed);
    }
}
