package com.stuypulse.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DrivetrainImpl extends Drivetrain {
    
    private SparkMax[] leftMotors;
    private SparkMax[] rightMotors; 

    private SparkMaxConfig motorConfig;


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

        SparkMaxConfig motorConfig = new SparkMaxConfig();
        motorConfig.smartCurrentLimit(Settings.Drivetrain.DRIVE_MOTOR_CURRENT_LIMIT);

        // Back wheel config
        // back left will follow front left, safe parameters will persist; config will persist across power cycles
        motorConfig.follow(leftMotors[0]);
        leftMotors[1].configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        motorConfig.follow(rightMotors[0]);
        rightMotors[1].configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Front wheel config
        motorConfig.disableFollowerMode();
        rightMotors[0].configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        motorConfig.inverted(true); 
        leftMotors[0].configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    
    @Override
    public void driveArcade(double xSpeed, double zRotation, boolean squared) {
        drive.arcadeDrive(xSpeed, zRotation, squared);
    }

    @Override
    public void driveTank(double leftSpeed, double rightSpeed, boolean squared) {
        drive.tankDrive(leftSpeed, rightSpeed, squared);
    }

    @Override
    public void periodic() {}
}   