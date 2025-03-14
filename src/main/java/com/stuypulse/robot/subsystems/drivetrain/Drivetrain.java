// Lucas, Sebastian, Daniel, Yunus

package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.robot.Robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;

public abstract class Drivetrain extends SubsystemBase {

    private static final Drivetrain instance;

    static {
        instance = new DrivetrainImpl();
    }

    public static Drivetrain getInstance() {
        return instance;
    }
    
    private SparkMax[] leftMotors;
    private SparkMax[] rightMotors;

    public abstract void driveArcade(double xSpeed, double zRotation, boolean squared);
    
    public abstract void driveTank(double leftSpeed, double rightSpeed, boolean squared);

    @Override
    public void periodic() {}
}