package com.stuypulse.robot.subsystems.pivot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.network.SmartNumber;


public class PivotImpl extends Pivot {

    private SparkMax pivotMotor;
    private SparkMax rollerMotor;
    double CurrentRollerSetSpeed;
    double CurrentPivotSetSpeed;
    // SmartNumber CurrentRollerSetSpeed = new SmartNumber("CurrentRollerSetSpeed", 0);
    // SmartNumber CurrentPivotSetSpeed = new SmartNumber("CurrentPivotSetSpeed", 0);

    public PivotImpl() {
        super();
        pivotMotor = new SparkMax(Ports.Pivot.PIVOT_MOTOR,MotorType.kBrushless);
        rollerMotor = new SparkMax(Ports.Pivot.ROLLER_MOTOR, MotorType.kBrushed);
    }

    @Override
    public void setRollerMotor(double speed) {
        CurrentRollerSetSpeed = speed;
        rollerMotor.set(speed);
    }

    @Override
    public void setPivotMotor(double speed) {
        CurrentPivotSetSpeed = speed;
        pivotMotor.set(speed);
    }

    @Override
    public void periodic() {
        super.periodic();
    }    
    
    
}
