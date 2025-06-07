package com.stuypulse.robot.subsystems.climber;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Motors.ClimbConfig;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimbImpl extends Climb {
    private SparkMax climbMotor;
    private RelativeEncoder climbEncoder;

    public ClimbImpl() {
        super();
        climbMotor = new SparkMax(Ports.Climb.CLIMB_MOTOR, MotorType.kBrushless);

        Motors.ClimbConfig.CLIMB_MOTOR_CONFIG.encoder
                .positionConversionFactor(
                        Settings.Climb.CLIMB_MOTOR_GEAR_RATIO * Settings.Climb.CLIMB_MOTOR_REDUCTION_FACTOR);

        climbMotor.configure(ClimbConfig.CLIMB_MOTOR_CONFIG, ResetMode.kNoResetSafeParameters,
                PersistMode.kNoPersistParameters);
        climbEncoder = climbMotor.getEncoder();
    }

    @Override
    public Rotation2d getCurrentAngle() {
        return Rotation2d.fromRotations(climbEncoder.getPosition());
    }

    @Override
    public boolean atTargetAngle() {
        return Math.abs(getCurrentAngle().getDegrees()
                - getState().getTargetAngle().getDegrees()) < Settings.Climb.ANGLE_TOLERANCE.getDegrees();
    }

    @Override
    public void periodic() {
        super.periodic();

        if (!atTargetAngle()) {
            climbMotor.set(getState().getTargetMotorSpeed());
        } else {
            climbMotor.set(0.0);
        }
        if(Settings.DEBUG_MODE) {
            SmartDashboard.putNumber("Climb/Angular Velocity", climbEncoder.getVelocity());
        }
        
        SmartDashboard.putNumber("Climb/Current Angle", getCurrentAngle().getDegrees());
        SmartDashboard.putNumber("Climb/Target angle", getState().getTargetAngle().getDegrees());
        SmartDashboard.putBoolean("Climb/At target angle", atTargetAngle());
    }
}
