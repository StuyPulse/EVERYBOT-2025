package com.stuypulse.robot.subsystems.drivetrain;

import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.revrobotics.spark.SparkMax;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    
    public abstract double getLeftDistance();
    public abstract double getRightDistance();
    public abstract Rotation2d getHeading();
    public abstract double getGyroRate();

    public abstract SysIdRoutine getSysIdRoutine();

    public abstract void resetPose();
    public abstract Pose2d getPose();

    public abstract double getOutputVoltage(SparkMax motor);
    public abstract Command followPathCommand(PathPlannerPath ppPath);

    public abstract void driveToNearestAprilTag();
    public abstract Command findPath(Pose2d targetPose, PathConstraints constraints, double endSpeed);
    public abstract Command findPathToPath( PathConstraints constraints, PathPlannerPath path);
    public abstract DifferentialDriveKinematics getKinematics();
    public abstract DifferentialDriveOdometry getOdometry();

    public abstract void configureAutoBuilder();

    @Override
    public void periodic() {}
}
