package com.stuypulse.robot.subsystems.pivot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class PivotImpl extends Pivot {

    private SparkMax pivotMotor;
    private SparkMax rollerMotor;

    public PivotImpl() {
        super();
        pivotMotor = new SparkMax(Ports.Pivot.PIVOT_MOTOR,MotorType.kBrushless);
        rollerMotor = new SparkMax(Ports.Pivot.ROLLER_MOTOR, MotorType.kBrushed);
    }

    @Override
    public void rollersAcquire() {
        rollerMotor.set(Settings.Pivot.ALGAE_INTAKE_SPEED.get());
    }

    @Override
    public void rollersDeacquire() {
        rollerMotor.set(Settings.Pivot.ALGAE_SHOOT_SPEED.get());
    }

    @Override
    public void setRollersStill() {
        rollerMotor.set(Settings.Pivot.ALGAE_HOLDING_SPEED.get()); 
    }

    @Override
    public void setRollerMotor(double speed) {
        rollerMotor.set(speed);
    }

    @Override
    public void setPivotMotor(double speed) {
        pivotMotor.set(speed);
    }

    @Override
    public void periodic() {
        super.periodic();
    }    
    
    
}
