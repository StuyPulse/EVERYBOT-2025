package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Gains;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Motors.DrivetrainConfig;
import com.stuypulse.robot.subsystems.odometry.Odometry;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;
import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.Volts;

import java.util.function.Supplier;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

public class DrivetrainImpl extends Drivetrain {
    private final AHRS gyro = new AHRS();

    private final SparkMax[] leftMotors;
    private final SparkMax[] rightMotors;
    private final RelativeEncoder leftEncoder;
    private final RelativeEncoder rightEncoder;

    private final DifferentialDrive drive;
    private final DifferentialDriveOdometry odometry;
    private final DifferentialDriveKinematics kinematics;
    public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
    private double driveSpeedModifier = 1;
    private SimpleMotorFeedforward angularArcadeFeedforward;
    private SimpleMotorFeedforward velocityArcadeFeedfoward;

    private RobotConfig pathPlannerRobotConfig;

    public DrivetrainImpl() {
        super();
        leftMotors = new SparkMax[] {
                new SparkMax(Ports.Drivetrain.LEFT_LEAD, MotorType.kBrushless),
                new SparkMax(Ports.Drivetrain.LEFT_FOLLOW, MotorType.kBrushless)
        };
        rightMotors = new SparkMax[] {
                new SparkMax(Ports.Drivetrain.RIGHT_LEAD, MotorType.kBrushless),
                new SparkMax(Ports.Drivetrain.RIGHT_FOLLOW, MotorType.kBrushless)
        };

        drive = new DifferentialDrive(leftMotors[0], rightMotors[0]);

        Motors.DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.encoder
                .positionConversionFactor(
                        Constants.Drivetrain.DRIVETRAIN_GEAR_RATIO * Constants.Drivetrain.WHEEL_CIRCUMFERENCE_METERS)
                .velocityConversionFactor(
                        Constants.Drivetrain.DRIVETRAIN_GEAR_RATIO * Constants.Drivetrain.WHEEL_CIRCUMFERENCE_METERS
                                / 60);

        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.follow(leftMotors[0]);
        leftMotors[1].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.follow(rightMotors[0]);
        rightMotors[1].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        // Front wheel config
        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.disableFollowerMode();
        rightMotors[0].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.inverted(true);
        leftMotors[0].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.inverted(true);
        leftMotors[0].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        // Left Lead
        leftEncoder = leftMotors[0].getEncoder();
        // Right Lead
        rightEncoder = rightMotors[0].getEncoder();

        leftMotors[0].setCANTimeout(250);
        leftMotors[1].setCANTimeout(250);
        rightMotors[0].setCANTimeout(250);
        rightMotors[1].setCANTimeout(250);

        // Odometry, Kinematics, Controllers, Vision
        kinematics = new DifferentialDriveKinematics(Constants.Drivetrain.TRACK_WIDTH_METERS);
        odometry = new DifferentialDriveOdometry(getHeading(), getLeftDistance(), getRightDistance());

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
    public void driveArcade(double xSpeed, double zRotation, boolean squared) {
        if(!Settings.EnabledSubsystems.DRIVETRAIN.get()) return;
        
        drive.arcadeDrive(xSpeed, zRotation, squared);
        SmartDashboard.putString("Drivetrain/Drivetrain Mode", "Arcade Drive");
    }

    @Override
    public void driveTank(double leftSpeed, double rightSpeed, boolean squared) {
        if(!Settings.EnabledSubsystems.DRIVETRAIN.get()) return;
        
        drive.tankDrive(leftSpeed, rightSpeed, squared);
        SmartDashboard.putString("Drivetrain/Drivetrain Mode", "Tank Drive");
    }

    public void driveTankVolts(Double lVolts, Double rVolts) {
        if(!Settings.EnabledSubsystems.DRIVETRAIN.get()) return;
        
        leftMotors[0].setVoltage(lVolts);
        rightMotors[0].setVoltage(rVolts);
        drive.feed();
    }

    private void updateOdometry() {
        odometry.update(getHeading(), getLeftDistance(), getRightDistance());
    }

    public void resetOdometry(Pose2d newPose) {
        odometry.resetPose(newPose);
    }

    /**
     * returns the left motor velocity
     * 
     * @return velocity in meters per seconds
     */
    public double getLeftVelocity() {
        return -leftEncoder.getVelocity();
    }

    /**
     * returns the right motor velocity
     * 
     * @return velocity in meters per seconds
     */
    public double getRightVelocity() {
        return -rightEncoder.getVelocity();
    }

    private DifferentialDriveWheelSpeeds getSpeeds() {
        DifferentialDriveWheelSpeeds wheelspeeds = new DifferentialDriveWheelSpeeds(getLeftVelocity(),getRightVelocity());
        return wheelspeeds;
    }

    @Override
    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(-gyro.getAngle()); 
    }

    @Override
    public double getGyroRate() {
        return gyro.getRate();
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

            driveTankVolts(leftSpeed, rightSpeed);
        },
        new PPLTVController(Settings.Drivetrain.ppQelems, Settings.Drivetrain.ppRelems, 0.02, 9),
        pathPlannerRobotConfig,
        () -> {
            var alliance = DriverStation.getAlliance();

            return alliance.isPresent() ? alliance.get() == DriverStation.Alliance.Red : true;
        },
        this);
    }

    private ChassisSpeeds getChassisSpeeds() {
        return kinematics.toChassisSpeeds(getSpeeds());
    }

    @Override
    public double getLeftDistance() {
        return -leftEncoder.getPosition();
    }

    @Override
    public double getRightDistance() {
        return -rightEncoder.getPosition();
    }

    @Override
    public void resetPose() {
        Odometry robotOdometry = Odometry.getInstance();
        odometry.resetPosition(gyro.getRotation2d(), getLeftDistance(), getRightDistance(), robotOdometry.getEstimatedPose());
    }

    @Override
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    @Override
    public double getOutputVoltage(SparkMax motor) {
        return motor.getAppliedOutput() * motor.getBusVoltage();
    }

    @Override
    public SysIdRoutine getSysIdRoutine() {
        return new SysIdRoutine(
                new SysIdRoutine.Config(),
                new SysIdRoutine.Mechanism(
                        voltage -> {
                            leftMotors[0].setVoltage(voltage);
                            rightMotors[0].setVoltage(voltage);
                            drive.feed();
                        },
                        log -> {
                            log.motor("drive-left")
                                    .voltage(Voltage.ofBaseUnits(getOutputVoltage(leftMotors[0]), Volts))
                                    .linearPosition(Meters.ofBaseUnits(getLeftDistance()))
                                    .linearVelocity(MetersPerSecond.ofBaseUnits(getLeftVelocity()));
                            log.motor("drive-right")
                                    .voltage(Voltage.ofBaseUnits(getOutputVoltage(rightMotors[0]), Volts))
                                    .linearPosition(Meters.ofBaseUnits(getRightDistance()))
                                    .linearVelocity(MetersPerSecond.ofBaseUnits(getRightVelocity()));
                        },
                        this));
    }

    @Override
    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }

    @Override
    public DifferentialDriveOdometry getOdometry() {
        return odometry;
    }

    @Override
    public void pathfindThenFollowPath(PathConstraints constraints, PathPlannerPath path) {
        AutoBuilder.pathfindThenFollowPath(path, constraints)
        .unless(() -> Math.abs(driver.getLeftStick().y) > 0.1 || Math.abs(driver.getRightStick().x) > 0.1 )
        .schedule();  
    }

    @Override
    public void setSpeedModifier(double targetSpeedModifier) {
        this.driveSpeedModifier = targetSpeedModifier;
    }

    @Override
    public double getSpeedModifier() {
        return driveSpeedModifier;
    }

    @Override
    public Supplier<Double> velocityFFCalculate(double input) {
        return () -> velocityArcadeFeedfoward.calculate(input);
    }

    @Override
    public Supplier<Double> angularPIDCalculate(double input) {
        return () -> angularArcadeFeedforward.calculate(input);
    }


    @Override
    public void periodic() {
        super.periodic();

        updateOdometry(); 

        SmartDashboard.putNumber("Drivetrain/ Joystick Left x", driver.getLeftStick().x);
        SmartDashboard.putNumber("Drivetrain/Left applied voltage", getOutputVoltage(leftMotors[0]));
        SmartDashboard.putNumber("Drivetrain/Right applied voltage", getOutputVoltage(rightMotors[0]));
        SmartDashboard.putNumber("Drivetrain/Left distance", getLeftDistance());
        SmartDashboard.putNumber("Drivetrain/Right distance", getRightDistance());
        SmartDashboard.putNumber("Drivetrain/Left velocity", getLeftVelocity());
        SmartDashboard.putNumber("Drivetrain/Right velocity", getRightVelocity());
        SmartDashboard.putNumber("Drivetrain/velocity pid outtake", angularArcadeFeedforward.calculate(driver.getLeftStick().y));
        SmartDashboard.putNumber("Drivetrain/angular pid outtake", angularArcadeFeedforward.calculate(driver.getRightStick().y));
        SmartDashboard.putNumber("Drivetrain/Speed Modifier", driveSpeedModifier);
    }
}
