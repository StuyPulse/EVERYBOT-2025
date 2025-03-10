package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.robot.Robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;

public abstract class Drivetrain extends SubsystemBase {

    private static final Drivetrain instance;

    static {
        if (Robot.isReal()){
            instance = new DrivetrainImpl();
        } else {
            instance = new DrivetrainSim();
        }
    }

    public static Drivetrain getInstance() {
        return instance;
    }
    
    private SparkMax[] leftMotors;
    private SparkMax[] rightMotors;


    public Drivetrain() {
        
    }
}