package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj2.command.Command;
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

    public abstract SysIdRoutine getSysIdRoutine();

    public abstract void resetPose();

    public abstract Pose2d getPose();

    public abstract Command getAutonomousCommand();

    @Override
    public void periodic() {}
}
