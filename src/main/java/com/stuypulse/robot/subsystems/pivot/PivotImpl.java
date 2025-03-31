package com.stuypulse.robot.subsystems.pivot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class PivotImpl extends Pivot {

    private SparkMax pivotMotor;
    private RelativeEncoder pivotEncoder;

    private SparkMax rollerMotor;
    double CurrentRollerSetSpeed;
    double CurrentPivotSetSpeed;
    // SmartNumber CurrentRollerSetSpeed = new SmartNumber("CurrentRollerSetSpeed", 0);
    // SmartNumber CurrentPivotSetSpeed = new SmartNumber("CurrentPivotSetSpeed", 0);

    public PivotImpl() {
        super();
        pivotMotor = new SparkMax(Ports.Pivot.PIVOT_MOTOR,MotorType.kBrushless);
        rollerMotor = new SparkMax(Ports.Pivot.ROLLER_MOTOR, MotorType.kBrushed);
        pivotMotor.configure(Motors.PivotConfig.PIVOT_MOTOR_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
        pivotEncoder = pivotMotor.getEncoder();
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
        
        SmartDashboard.putNumber("Pivot/Current Angle", (pivotEncoder.getPosition() * 360) % 360);
    }       
}
