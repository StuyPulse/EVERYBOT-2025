package com.stuypulse.robot.subsystems.pivot;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.stuylib.control.feedforward.ArmFeedforward;
import com.stuypulse.stuylib.control.feedforward.MotorFeedforward;

import edu.wpi.first.math.controller.PIDController;

public class PivotImpl extends Pivot {

    private SparkMax pivotMotor;
    private SparkMax rollerMotor;

    private AbsoluteEncoder pivotEncoder;

    public PivotImpl() {
        super();
        pivotMotor = new SparkMax(Ports.Pivot.PIVOT_MOTOR,MotorType.kBrushless);
        rollerMotor = new SparkMax(Ports.Pivot.ROLLER_MOTOR, MotorType.kBrushless);
        
        pivotEncoder = pivotMotor.getAbsoluteEncoder();
        
        controller = new MotorFeedforward(null, null, null)
            .add()
            .add(new PIDController(null, null, null))
            .
    }
    
    
    
}
