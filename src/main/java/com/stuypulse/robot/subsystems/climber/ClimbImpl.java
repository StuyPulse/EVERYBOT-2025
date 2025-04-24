package com.stuypulse.robot.subsystems.climber;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Gains;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Motors.ClimbConfig;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.geometry.Rotation2d;

public class ClimbImpl extends Climb {
    private SparkMax climbMotor;
    private RelativeEncoder climbEncoder;

    private BangBangController climbController;

    public ClimbImpl() {
        super();
        climbMotor = new SparkMax(Ports.Climb.CLIMB_MOTOR, MotorType.kBrushless);
        climbEncoder = climbMotor.getEncoder();
        climbMotor.configure(ClimbConfig.CLIMB_MOTOR_CONFIG, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        Motors.ClimbConfig.CLIMB_MOTOR_CONFIG.encoder
            .positionConversionFactor(Settings.Climb.CLIMB_MOTOR_GEAR_RATIO * Settings.Climb.CLIMB_MOTOR_REDUCTION_FACTOR);

        climbController = new BangBangController();
    }

    @Override
    public Rotation2d getCurrentAngle(){
        return Rotation2d.fromRotations(climbEncoder.getPosition() - Constants.Climb.CLIMBER_OFFSET.getRotations());
    }

    @Override
    public boolean atTargetAngle() {
        return (getCurrentAngle().getDegrees() - getState().getTargetAngle().getDegrees()) > Settings.Climb.ANGLE_TOLERANCE.getDegrees();
    }

    @Override
    public void periodic(){
        super.periodic();
        climbMotor.setVoltage(climbController.calculate(climbEncoder.getPosition(), getState().getTargetAngle().getDegrees()));
    }
}
