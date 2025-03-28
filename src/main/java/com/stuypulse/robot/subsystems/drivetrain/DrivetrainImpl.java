package com.stuypulse.robot.subsystems.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Motors.DrivetrainConfig;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class DrivetrainImpl extends Drivetrain {
    
    private final AHRS gyro = new AHRS();

    private final SparkMax[] leftMotors;
    private final SparkMax[] rightMotors;

    private final RelativeEncoder leftEncoder;
    private final RelativeEncoder rightEncoder;

    private final DifferentialDrive drive;
    private final DifferentialDriveOdometry odometry;

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

        // Left Lead
        leftEncoder = leftMotors[0].getEncoder();
        // Right Lead
        rightEncoder = rightMotors[0].getEncoder();

        odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0);
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

    private double getRotation() {
        double distance = leftEncoder.getPosition() - rightEncoder.getPosition();
        return Math.toDegrees(distance / Settings.Drivetrain.TRACK_WIDTH);
    }

    private void updateOdometry() {
        odometry.update(gyro.getRotation2d(), leftEncoder.getPosition(), rightEncoder.getPosition());
        
        // Without Gyro:
        // odometry.update(Rotation2d.fromDegrees(getRotation()), leftEncoder.getPosition() * Settings.Drivetrain.WHEEL_CIRCUMFERENCE, rightEncoder.getPosition() * Settings.Drivetrain.WHEEL_CIRCUMFERENCE);
    }

    @Override
    public double getLeftVelocity() {
        return leftEncoder.getVelocity();
    }

    @Override
    public double getRightVelocity() {
        return rightEncoder.getVelocity();
    }

    @Override
    public void periodic() {
        updateOdometry();
    }
}   