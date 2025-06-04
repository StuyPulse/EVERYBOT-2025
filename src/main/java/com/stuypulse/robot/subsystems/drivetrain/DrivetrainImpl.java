package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.control.feedforward.MotorFeedforward;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Gains;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Motors.DrivetrainConfig;
import com.stuypulse.robot.subsystems.vision.LimelightVision;

import java.sql.Driver;
import java.util.List;
import java.util.Vector;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import com.pathplanner.lib.util.DriveFeedforwards;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.DifferentialDriveFeedforward;
import edu.wpi.first.math.controller.DifferentialDriveWheelVoltages;
import edu.wpi.first.math.controller.LTVUnicycleController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.proto.Kinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.Volts;
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

    // private final LimelightVision vision;
    private double visionDrive;
    private double visionSteer;

    private RobotConfig pathPlannerRobotConfig;

    private SimpleMotorFeedforward ffController;
    private PIDController lPIDController = new PIDController(Gains.Drivetrain.PID.left.kP, Gains.Drivetrain.PID.left.kI,
            Gains.Drivetrain.PID.left.kD);
    private PIDController rPIDController = new PIDController(Gains.Drivetrain.PID.right.kP,
            Gains.Drivetrain.PID.right.kI, Gains.Drivetrain.PID.right.kD);

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

        controllerPosition = new MotorFeedforward(Gains.Drivetrain.FF.kS, Gains.Drivetrain.FF.kV,
                Gains.Drivetrain.FF.kA)
                .position();

        ffController = new SimpleMotorFeedforward(Gains.Drivetrain.FF.kS, Gains.Drivetrain.FF.kV,
                Gains.Drivetrain.FF.kA);

        // vision = LimelightVision.getInstance();

        // PathPlanner robot configuration
        try {
            pathPlannerRobotConfig = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            pathPlannerRobotConfig = null;
        }
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

    public void driveTankVolts(Double lVolts, Double rVolts) {
        leftMotors[0].setVoltage(lVolts);
        rightMotors[0].setVoltage(rVolts);
        drive.feed();
        SmartDashboard.putNumber("Drivetrain/ left volts", lVolts);
        SmartDashboard.putNumber("Drivetrain/ right volts", rVolts);
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
    public double getLeftVelocity() { // In Meters per seconds
        return -leftEncoder.getVelocity();
    }

    /**
     * returns the right motor velocity
     * 
     * @return velocity in meters per minute
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
        LimelightVision vision = LimelightVision.getInstance();
        AutoBuilder.configure(
        vision::getEstimatedPose,
        vision::resetEstimatedPose,
        this::getChassisSpeeds,
        (speeds) -> {
            DifferentialDriveWheelSpeeds convertedSpeeds = kinematics.toWheelSpeeds(speeds);
            double leftSpeed = convertedSpeeds.leftMetersPerSecond;
            double rightSpeed = convertedSpeeds.rightMetersPerSecond;
            SmartDashboard.putNumber("Drivetrain/ PP Right speed", rightSpeed);
            SmartDashboard.putNumber("Drivetrain/ PP left speed ", leftSpeed);
            driveTankVolts(-leftSpeed, -rightSpeed);
        },
       new PPLTVController(VecBuilder.fill(0.0725, 0.125, 1), VecBuilder.fill(1,2), 0.02, 9),
    //    new PPLTVController(0.02)
        pathPlannerRobotConfig,
        () -> {
        var alliance = DriverStation.getAlliance();

        return alliance.isPresent() ? alliance.get() == DriverStation.Alliance.Red  : true;
        },
        this);
    }

    public void closedLoopControl(ChassisSpeeds speeds) {
        DifferentialDriveWheelSpeeds wheelspeeds = kinematics.toWheelSpeeds(speeds);
        wheelspeeds.desaturate(Constants.Drivetrain.MAX_VELOCITY_METERS_PER_SECOND);

        DifferentialDriveWheelVoltages feedbackVoltages = new DifferentialDriveWheelVoltages(
            lPIDController.calculate(getSpeeds().leftMetersPerSecond,
                 wheelspeeds.leftMetersPerSecond),
            rPIDController.calculate(getSpeeds().rightMetersPerSecond,
                wheelspeeds.rightMetersPerSecond)  
        );


        DifferentialDriveWheelVoltages feedFowarVoltages = new DifferentialDriveWheelVoltages(
            ffController.calculate(
                getSpeeds().leftMetersPerSecond,
                    wheelspeeds.leftMetersPerSecond),
            ffController.calculate(
                getSpeeds().rightMetersPerSecond,
                wheelspeeds.rightMetersPerSecond
            )
        );
        driveTankVolts(
            -MathUtil.clamp(feedbackVoltages.left + feedFowarVoltages.left, Settings.Drivetrain.DRIVE_UPPER_VOLTAGE_LIMIT, Settings.Drivetrain.DRIVE_LOWER_VOLTAGE_LIMIT), 
            -MathUtil.clamp(feedbackVoltages.right + feedFowarVoltages.right,Settings.Drivetrain.DRIVE_UPPER_VOLTAGE_LIMIT, Settings.Drivetrain.DRIVE_LOWER_VOLTAGE_LIMIT)
        );
    }

    public ChassisSpeeds getChassisSpeeds() {
        return kinematics.toChassisSpeeds(getSpeeds());
    }

    // public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    //     return kinematics.toWheelSpeeds(getSpeeds());
    // }

    @Override
    public double getLeftDistance() {
        return -leftEncoder.getPosition();
    }

    @Override
    public double getRightDistance() {
        return -rightEncoder.getPosition();
    }

    // Experimental, need confirmation that this is actually what sysId needs
    public LinearVelocity getMetersPerSecond(double velocity) {
        return MetersPerSecond.ofBaseUnits(velocity * Constants.Drivetrain.WHEEL_CIRCUMFERENCE_METERS / 60);
    }

    @Override
    public void resetPose() {
        LimelightVision vision = LimelightVision.getInstance();
        odometry.resetPosition(gyro.getRotation2d(), getLeftDistance(), getRightDistance(), vision.getEstimatedPose());
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
    public Command followPathCommand(PathPlannerPath ppPath) {
        final DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
                ffController, kinematics, 10);

        final TrajectoryConfig trajectoryConfig = new TrajectoryConfig(Constants.Autonomous.MAX_SPEED_METERS_PER_SECOND,
                Constants.Autonomous.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
                .setKinematics(kinematics)
                .addConstraint(voltageConstraint);

        final Trajectory exampleTrajectory = TrajectoryGenerator
                .generateTrajectory(
                    new Vector<Pose2d>(ppPath.getPathPoses()), 
                    trajectoryConfig);
        // TrajectoryGenerator.generateTrajectory(
        // new Pose2d(0, 0, new Rotation2d(0)), // START
        // List.of(new Translation2d(1, 1), new Translation2d(2, -1)), // INTERMEDIATE
        // SETPOINTS
        // new Pose2d(3, 0, new Rotation2d()), // END
        // trajectoryConfig);

        RamseteCommand command = new RamseteCommand(
                exampleTrajectory,
                this::getPose,
                new RamseteController(.7, 2.0),
                ffController,
                kinematics,
                this::getSpeeds,
                lPIDController,
                rPIDController,
                this::driveTankVolts,
                this);
        return Commands.runOnce(() -> resetOdometry(exampleTrajectory.getInitialPose()))
                .andThen(command)
                .andThen(Commands.runOnce(() -> driveTankVolts(0.0, 0.0))); // Stop Drivetrain
    }

    private void updateVision() { // TEMPORARY
        final double STEER_K = 0.03;
        final double DRIVE_K = 0.03;
        final double DESIRED_TARGET_AREA = 13.0;
        final double MAX_DRIVE = 0.3;

        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

        if (tv < 0) {
            this.visionDrive = 0.0;
            this.visionSteer = 0.0;
            return;
        }

        visionDrive = (DESIRED_TARGET_AREA - ta) * DRIVE_K;
        this.visionDrive = SLMath.clamp(visionDrive, 0.0, MAX_DRIVE);
        this.visionSteer = tx * STEER_K;
    }

    @Override
    public void driveToNearestAprilTag() {
        driveArcade(this.visionSteer, this.visionDrive, true);
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
    public Command findPath(Pose2d targetPose, PathConstraints constraints, double endSpeed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPath'");
    }

    @Override
    public Command findPathToPath(PathConstraints constraints, PathPlannerPath path) {
        return AutoBuilder.pathfindThenFollowPath(path, constraints);    
    }

    @Override
    public void periodic() {
        super.periodic();

        updateVision();
        updateOdometry();
        SmartDashboard.putNumber("Drivetrain/ left applied voltage", getOutputVoltage(leftMotors[0]));
        SmartDashboard.putNumber("Drivetrain/ right applied voltage", getOutputVoltage(rightMotors[0]));
        SmartDashboard.putNumber("Drivetrain/ left distance", getLeftDistance());
        SmartDashboard.putNumber("Drivetrain/ right distance", getRightDistance());
        SmartDashboard.putNumber("Drivetrain/ left velocity", getLeftVelocity());
        SmartDashboard.putNumber("Drivetrain/ Right velocity", getRightVelocity());
    }
}
