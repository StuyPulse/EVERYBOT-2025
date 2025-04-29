package com.stuypulse.robot.subsystems.climber;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Motors.ClimbConfig;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimbImpl extends Climb {
    private SparkMax climbMotor;
    private RelativeEncoder climbEncoder;

    private BangBangController climbController;

    public ClimbImpl() {
        super();
        climbMotor = new SparkMax(Ports.Climb.CLIMB_MOTOR, MotorType.kBrushless);

        Motors.ClimbConfig.CLIMB_MOTOR_CONFIG.encoder
                .positionConversionFactor(
                        Settings.Climb.CLIMB_MOTOR_GEAR_RATIO * Settings.Climb.CLIMB_MOTOR_REDUCTION_FACTOR);

        climbMotor.configure(ClimbConfig.CLIMB_MOTOR_CONFIG, ResetMode.kNoResetSafeParameters,
                PersistMode.kNoPersistParameters);
        climbEncoder = climbMotor.getEncoder();

        climbController = new BangBangController();
    }

    @Override
    public Rotation2d getCurrentAngle() {
        return Rotation2d.fromRotations(climbEncoder.getPosition() - Constants.Climb.CLIMBER_OFFSET.getRotations());
    }

    @Override
    public boolean atTargetAngle() {
        return Math.abs(getCurrentAngle().getDegrees()
                - getState().getTargetAngle().getDegrees()) < Settings.Climb.ANGLE_TOLERANCE.getDegrees();
    }

    @Override
    public void periodic() {
        super.periodic();
            if (getState()==ClimbState.CLIMBING && !atTargetAngle()) {
                climbMotor.set(Settings.Climb.CLIMBING_VOLTAGE);
            } else if(getState() == ClimbState.STOW && !atTargetAngle()) {
                climbMotor.set(Settings.Climb.STOW_VOLTAGE);
            }
            else {
            climbMotor.set(0.0);
            }
        // climbMotor.setVoltage(
        //         climbController.calculate(climbEncoder.getVelocity(), getState().getTargetAngle().getDegrees())
        //         * Settings.Climb.CLIMB_SPEED_MODIFIER
        // );
        // SmartDashboard.putNumber("climber output", climbController.calculate(climbEncoder.getVelocity(), getState().getTargetAngle().getDegrees()));
        SmartDashboard.putNumber("Climb/target angle", getState().getTargetAngle().getDegrees());
        SmartDashboard.putNumber("climb/encoder rate", climbEncoder.getVelocity());
        SmartDashboard.putBoolean("Climb/at target angle", atTargetAngle());
    }
}
