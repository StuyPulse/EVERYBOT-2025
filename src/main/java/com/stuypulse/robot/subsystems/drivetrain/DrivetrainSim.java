package com.stuypulse.robot.subsystems.drivetrain;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Volts;

import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.revrobotics.spark.SparkMax;
import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Gains;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.odometry.Odometry;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class DrivetrainSim extends Drivetrain {

    private EncoderSim leftEncoderSim;
    private EncoderSim rightEncoderSim;

    // private SimDevice gyro;
    private double driveSpeedModifier = 1;
    private SimpleMotorFeedforward angularArcadeFeedforward;
    private SimpleMotorFeedforward velocityArcadeFeedfoward;
    
    private DifferentialDrivetrainSim driveSim;

    private PWMSparkMax leftMotor;
    private PWMSparkMax rightMotor;

    private RobotConfig pathPlannerRobotConfig;

    public DrivetrainSim() {
        super();

        leftEncoderSim = new EncoderSim(new Encoder(0, Ports.Drivetrain.LEFT_LEAD));
        rightEncoderSim = new EncoderSim(new Encoder(0, Ports.Drivetrain.RIGHT_LEAD));

        leftMotor = new PWMSparkMax(Ports.Drivetrain.LEFT_LEAD);

        rightMotor = new PWMSparkMax(Ports.Drivetrain.RIGHT_LEAD);

        leftEncoderSim.setDistancePerPulse(2f * Math.PI * 3f / 8400f);
        rightEncoderSim.setDistancePerPulse(2f * Math.PI * 3f / 8400f);
        // gyro = new SimDevice(4);

        driveSim = new DifferentialDrivetrainSim(
                DCMotor.getNEO(1), // 2 NEO motors on each side of the drivetrain.
                1f/Constants.Drivetrain.DRIVETRAIN_GEAR_RATIO,
                6.883, // MOI of 7.5 kg m^2 (from CAD model).
                46.493, // The mass of the robot is 60 kg.
                Units.inchesToMeters(3), // The robot uses 3" radius wheels.
                Constants.Drivetrain.TRACK_WIDTH_METERS,
                // The standard deviations for measurement noise:
                // x and y: 0.001 m
                // heading: 0.001 rad
                // l and r velocity: 0.1 m/s
                // l and r position: 0.005 m
                VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005));
                
        // PathPlanner robot configuration
        try {
            pathPlannerRobotConfig = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            pathPlannerRobotConfig = null;
        }

        angularArcadeFeedforward = new SimpleMotorFeedforward(Gains.Drivetrain.arcadeFF.angularArcadeFF.kS, Gains.Drivetrain.arcadeFF.angularArcadeFF.kV, Gains.Drivetrain.arcadeFF.angularArcadeFF.kA);
        velocityArcadeFeedfoward = new SimpleMotorFeedforward(Gains.Drivetrain.arcadeFF.velocityArcadeFF.kS, Gains.Drivetrain.arcadeFF.velocityArcadeFF.kV, Gains.Drivetrain.arcadeFF.velocityArcadeFF.kA);
    }

    @Override
    public void periodic() {
        super.periodic();
        
        driveSim.setInputs(leftMotor.get() * 12, rightMotor.get() * 12);
        driveSim.update(.02);

        leftEncoderSim.setDistance(driveSim.getLeftPositionMeters());
        leftEncoderSim.setRate(driveSim.getLeftVelocityMetersPerSecond());
        rightEncoderSim.setDistance(driveSim.getRightPositionMeters());
        rightEncoderSim.setRate(driveSim.getRightVelocityMetersPerSecond());
    }

    @Override
    public void driveArcade(double xSpeed, double zRotation, boolean squared) {
        driveTank(kinematics.toWheelSpeeds(new ChassisSpeeds(xSpeed, xSpeed, zRotation * 15)).leftMetersPerSecond, kinematics.toWheelSpeeds(new ChassisSpeeds(xSpeed, xSpeed, zRotation * 15)).rightMetersPerSecond, true);
    }

    @Override
    public void driveTank(double leftSpeed, double rightSpeed, boolean squared) {
        leftMotor.set(-leftSpeed);
        rightMotor.set(-rightSpeed);
    }

    @Override
    public Rotation2d getHeading() {
        return Rotation2d.fromRotations(kinematics.toTwist2d(getLeftDistance(), getRightDistance()).dtheta/30f);
    }
    
    @Override
    public double getLeftDistance() {
        return leftEncoderSim.getDistance();
    }    

    @Override
    public double getRightDistance() {
        return rightEncoderSim.getDistance();
    }
        
    private Rotation2d prevHeading;

    @Override
    public double getGyroRate() {
        double rate = getHeading().minus(prevHeading).getRotations()*50;
        prevHeading = getHeading();
        return rate;
    }    

    @Override
    public SysIdRoutine getSysIdRoutine() {
        return new SysIdRoutine(
                new SysIdRoutine.Config(),
                new SysIdRoutine.Mechanism(
                        voltage -> {
                            leftMotor.setVoltage(voltage);
                            rightMotor.setVoltage(voltage);
                            //driveSim.feed();
                        },
                        log -> {
                            log.motor("drive-left")
                                    .voltage(Voltage.ofBaseUnits(leftMotor.getVoltage(), Volts))
                                    .linearPosition(Meters.ofBaseUnits(getLeftDistance()))
                                    .linearVelocity(MetersPerSecond.ofBaseUnits(getLeftVelocity()));
                            log.motor("drive-right")
                                    .voltage(Voltage.ofBaseUnits(rightMotor.getVoltage(), Volts))
                                    .linearPosition(Meters.ofBaseUnits(getRightDistance()))
                                    .linearVelocity(MetersPerSecond.ofBaseUnits(getRightVelocity()));
                        },
                        this));
    }

    @Override
    public void resetPose() {}

    @Override
    public Pose2d getPose() {
        return new Pose2d(getLeftDistance(), getRightDistance(), getHeading());
    }

    @Override
    public double getOutputVoltage(SparkMax motor) {
        return motor.getAppliedOutput() * motor.getBusVoltage();
    }

    @Override
    protected ChassisSpeeds getChassisSpeeds() {
        return kinematics.toChassisSpeeds(new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity()));
    }

    @Override
    protected double getLeftVelocity() {
        return leftEncoderSim.getRate();
    }

    @Override
    protected double getRightVelocity() {
        return rightEncoderSim.getRate();
    }

    @Override
    public void pathfindThenFollowPath(PathConstraints constraints, PathPlannerPath path){

    }

    @Override
    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }

    @Override
    public DifferentialDriveOdometry getOdometry() {
        return new DifferentialDriveOdometry(getHeading(), getLeftDistance(), getRightDistance());
    }

    @Override
    public void configureAutoBuilder() {
        Odometry odometry = Odometry.getInstance();
        
        AutoBuilder.configure(
        odometry::getEstimatedPose,
        odometry::resetEstimatedPose,
        this::getChassisSpeeds,
        (speeds) -> {
            DifferentialDriveWheelSpeeds convertedSpeeds = kinematics.toWheelSpeeds(speeds);

            double leftSpeed = -convertedSpeeds.leftMetersPerSecond;
            double rightSpeed = -convertedSpeeds.rightMetersPerSecond;

            SmartDashboard.putNumber("Drivetrain/PP Right speed", rightSpeed);
            SmartDashboard.putNumber("Drivetrain/PP left speed ", leftSpeed);

            driveTank(leftSpeed/12f, rightSpeed/12f, true);
        },
        new PPLTVController(Settings.Drivetrain.ppQelems, Settings.Drivetrain.ppRelems, 0.02),
        pathPlannerRobotConfig,
        () -> {
            var alliance = DriverStation.getAlliance();

            return alliance.isPresent() ? alliance.get() == DriverStation.Alliance.Red : true;
        },
        this);
    }

    @Override
    public void setSpeedModifier(double targetSpeedModifier) {
        this.driveSpeedModifier = targetSpeedModifier;
    }

    @Override
    public double getSpeedModifier() {
        return this.driveSpeedModifier;
    }

    @Override
    public Supplier<Double> velocityFFCalculate(double input) {
        return () -> velocityArcadeFeedfoward.calculate(input);
    }

    @Override
    public Supplier<Double> angularPIDCalculate(double input) {
        return () -> angularArcadeFeedforward.calculate(input);
    }
}
