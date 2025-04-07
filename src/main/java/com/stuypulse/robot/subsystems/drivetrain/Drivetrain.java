// Lucas O, Sebastian, Daniel, Yunus

package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.MotorFeedforward;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public abstract class Drivetrain extends SubsystemBase {

    private static final Drivetrain instance;

    public Controller controllerPosition;
    public AngleController controllerRotation;

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