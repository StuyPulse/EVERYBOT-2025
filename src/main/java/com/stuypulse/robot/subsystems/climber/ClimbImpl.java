package com.stuypulse.robot.subsystems.climber;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Ports;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbImpl extends Climb {

    private SparkMax climbMotor;
    private RelativeEncoder climbEncoder;


    public ClimbImpl(){
        super();
        climbMotor = new SparkMax(Ports.Climb.CLIMB_MOTOR, MotorType.kBrushed);
        climbEncoder = climbMotor.getAlternateEncoder();
        SparkMaxConfig climbConfig = new SparkMaxConfig();
        climbConfig.smartCurrentLimit(Settings.Climb.CLIMB_CURRENT);
        climbMotor.configure(climbConfig, null, null);
    }
    
    private Rotation2d getTargetAngle(){
        return getState().getTargetAngle();
    }

    @Override
    public Rotation2d getCurrentAngle(){
        return Rotation2d.fromRotations(climbEncoder.getPosition() - Constants.Climb.CLIMBER_OFFSET.getRotations());
    }

    @Override
    public void periodic(){
        super.periodic();
        double angleErrorDegrees = getTargetAngle().getDegrees() - getCurrentAngle().getDegrees();
        if (getState() == ClimbState.STOW){
            climbMotor.setVoltage(Constants.Climb.STOW_VOLTAGE);
        }
        else if (getState() == ClimbState.EXTEND) {
            if(angleErrorDegrees >  Constants.Climb.ANGLE_TOLERANCE){
                climbMotor.setVoltage(Constants.Climb.EXTEND_VOLTAGE);
            } else { 
                climbMotor.setVoltage(Constants.Climb.DEFAULT_VOLTAGE); 
            }
        } 
        else if (getState() == ClimbState.CLIMBING) {
            climbMotor.setVoltage(Constants.Climb.CLIMBING_VOLTAGE);
        }
    }

}