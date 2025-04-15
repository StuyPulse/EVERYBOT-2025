package com.stuypulse.robot.subsystems.climber;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Gains;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.ArmFeedforward;
import com.stuypulse.stuylib.control.feedforward.MotorFeedforward;
import com.stuypulse.robot.constants.Motors.ClimbConfig;

import edu.wpi.first.math.geometry.Rotation2d;

public class ClimbImpl extends Climb {
    private SparkMax climbMotor;
    private RelativeEncoder climbEncoder;

    private Controller controller;

    public ClimbImpl() {
        super();
        climbMotor = new SparkMax(Ports.Climb.CLIMB_MOTOR, MotorType.kBrushless);
        climbEncoder = climbMotor.getEncoder();
        climbMotor.configure(ClimbConfig.CLIMB_MOTOR_CONFIG, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        controller = new MotorFeedforward(Gains.Climb.FF.kS, Gains.Climb.FF.kV, Gains.Climb.FF.kA).position()
            .add(new ArmFeedforward(Gains.Climb.FF.kG))
            .add(new PIDController(Gains.Climb.PID.kP, Gains.Climb.PID.kI, Gains.Climb.PID.kD));
    }

    @Override
    public Rotation2d getCurrentAngle(){
        return Rotation2d.fromRotations(climbEncoder.getPosition() - Constants.Climb.CLIMBER_OFFSET.getRotations());
    }

    @Override
    public boolean atTargetAngle() {
        return (getState().getTargetAngle().getDegrees() - getCurrentAngle().getDegrees()) > Settings.Climb.ANGLE_TOLERANCE.getDegrees();
    }

    @Override
    public void periodic(){
        super.periodic();
        climbMotor.setVoltage(controller.update(getState().getTargetAngle().getDegrees(), getCurrentAngle().getDegrees()));
    }
}
