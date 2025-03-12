package com.stuypulse.robot.subsystems.pivot;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.ArmFeedforward;

public class PivotImpl extends Pivot {

    private SparkMax pivotMotor;
    private SparkMax rollerMotor;
    private ArmFeedforward armController;
    private ProfiledPIDController armPIDController;

    private AbsoluteEncoder pivotEncoder;

    public PivotImpl() {
        super();
        pivotMotor = new SparkMax(Ports.Pivot.PIVOT_MOTOR,MotorType.kBrushless);
        rollerMotor = new SparkMax(Ports.Pivot.ROLLER_MOTOR, MotorType.kBrushless);
        
        pivotEncoder = pivotMotor.getAbsoluteEncoder();
        armController = new ArmFeedforward(3,3,3,3);
    }

    @Override
    public void rollersAcquire() {
        rollerMotor.set(Settings.Rollers.ALGAE_INTAKE_SPEED.get());
    }

    @Override
    public void rollersDeacquire() {
        rollerMotor.set(Settings.Rollers.ALGAE_SHOOT_SPEED.get());
    }

    @Override
    public void setRollersStill() {
        rollerMotor.set(Settings.Rollers.ALGAE_STAYING_STILL_SPEED.get()); // just 0 LOL
    }

    @Override
    public boolean atTargetAngle() {
        /*if ((getPivotState().getTargetAngle() - Settings.Pivot.ANGLE_ERROR < getCurrentAngle()) && 
        (getPivotState().getTargetAngle() + Settings.Pivot.ANGLE_ERROR > getCurrentAngle())) {
            return true;
        }*/ // was broken xD
        return false;
    }

    @Override
    public double getCurrentAngle() { // not rotation2d
        return pivotEncoder.getPosition();
    }

    @Override
    public void setRollerMotor(double targetSpeed) {
        rollerMotor.set(targetSpeed);
    }

    @Override
    public void periodic() {
        super.periodic();
        // "DOUBLE CHECK THIS IM LIKE 100% SURE ITS WRONG LOL" - Edgar
        pivotMotor.setVoltage(armController.calculate(pivotEncoder.getPosition(),getPivotState().getTargetAngle().getRadians()));
    }
    
    
    
}
