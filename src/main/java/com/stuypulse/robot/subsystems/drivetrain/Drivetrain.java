package com.stuypulse.robot.subsystems.drivetrain;

import java.util.function.Supplier;

import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

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
    
    public abstract double getLeftDistance();
    public abstract double getRightDistance();
    public abstract Rotation2d getHeading();
    public abstract double getGyroRate();

    public abstract SysIdRoutine getSysIdRoutine();

    public abstract void resetPose();
    public abstract Pose2d getPose();

    public abstract double getOutputVoltage(SparkMax motor);

    public abstract void pathfindThenFollowPath(PathConstraints constraints, PathPlannerPath path);

    public abstract DifferentialDriveKinematics getKinematics();
    public abstract DifferentialDriveOdometry getOdometry();

    public abstract void configureAutoBuilder();

    public abstract void setSpeedModifier(double targetSpeedModifier);

    public abstract double getSpeedModifier();

    public abstract Supplier<Double> velocityPIDCalculate(double input);
    public abstract Supplier<Double> angularPIDCalculate(double input);

    @Override
    public void periodic() {}
}
