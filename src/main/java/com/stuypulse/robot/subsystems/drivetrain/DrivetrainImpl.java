package com.stuypulse.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Motors.DrivetrainConfig;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class DrivetrainImpl extends Drivetrain {
    
    private SparkMax[] leftMotors;
    private SparkMax[] rightMotors; 


    private final DifferentialDrive drive;

    public DrivetrainImpl() {
        super();
        leftMotors = new SparkMax[] {
                    new SparkMax(Ports.Drivetrain.LEFT_LEAD, MotorType.kBrushed),
                    new SparkMax(Ports.Drivetrain.LEFT_FOLLOW, MotorType.kBrushed)
                };
        rightMotors = new SparkMax[] {
                    new SparkMax(Ports.Drivetrain.RIGHT_LEAD, MotorType.kBrushed),
                    new SparkMax(Ports.Drivetrain.RIGHT_FOLLOW, MotorType.kBrushed)
        };

        drive = new DifferentialDrive(leftMotors[0], rightMotors[0]);
        
        leftMotors[0].setCANTimeout(250);
        leftMotors[1].setCANTimeout(250);
        rightMotors[0].setCANTimeout(250);
        rightMotors[1].setCANTimeout(250);

        // Back wheel config
        // back left will follow front left, safe parameters will persist; config will persist across power cycles
        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.follow(leftMotors[0]);
        leftMotors[1].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.follow(rightMotors[0]);
        rightMotors[1].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Front wheel config
        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.disableFollowerMode();
        rightMotors[0].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.inverted(true); 
        leftMotors[0].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    
    @Override
    public void driveArcade(double xSpeed, double zRotation, boolean squared) {
        drive.arcadeDrive(xSpeed, zRotation, squared);
        SmartDashboard.putString("Drivetrain/Drivetrain Mode", "Arcade Drive");
    }

    @Override
    public void driveTank(double leftSpeed, double rightSpeed, boolean squared) {
        drive.tankDrive(leftSpeed, rightSpeed, squared);
        SmartDashboard.putString("Drivetrain/Drivetrain Mode", "Tank Drive");
    }

    @Override
    public void periodic() {}
}   