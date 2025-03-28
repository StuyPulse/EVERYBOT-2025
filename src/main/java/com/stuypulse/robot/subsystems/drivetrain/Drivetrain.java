// Lucas O, Sebastian, Daniel, Yunus

package com.stuypulse.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Drivetrain extends SubsystemBase {

    private static final Drivetrain instance;

    static {
        instance = new DrivetrainImpl();
    }

    public static Drivetrain getInstance() {
        return instance;
    }

    public abstract void driveArcade(double xSpeed, double zRotation, boolean squared);
    
    public abstract void driveTank(double leftSpeed, double rightSpeed, boolean squared);
    
    public abstract double getLeftVelocity();

    public abstract double getRightVelocity();

    @Override
    public void periodic() {}
}