package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.stuylib.control.feedforward.MotorFeedforward;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Gains;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Motors.DrivetrainConfig;

import java.util.List;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

    private final Field2d field = new Field2d();
    private double visionDrive;
    private double visionSteer;

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
                        Constants.Drivetrain.DRIVETRAIN_GEAR_RATIO * Constants.Drivetrain.WHEEL_CIRCUMFERENCE_METERS);

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

        // Odometry, Kinematics, Controllers
        kinematics = new DifferentialDriveKinematics(Constants.Drivetrain.TRACK_WIDTH_METERS);
        odometry = new DifferentialDriveOdometry(getHeading(), getLeftDistance(), getRightDistance());

        controllerPosition = new MotorFeedforward(Gains.Drivetrain.FF.kS, Gains.Drivetrain.FF.kV,
                Gains.Drivetrain.FF.kA)
                .position();

        ffController = new SimpleMotorFeedforward(Gains.Drivetrain.FF.kS, Gains.Drivetrain.FF.kV,
                Gains.Drivetrain.FF.kA);
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
    }

    private void updateOdometry() {
        odometry.update(getHeading(), getLeftDistance(), getRightDistance());
    }

    public void resetOdometry(Pose2d newPose) {
        odometry.resetPose(newPose);
    }

    public double getLeftVelocity() {
        return leftEncoder.getVelocity();
    }

    public double getRightVelocity() {
        return rightEncoder.getVelocity();
    }

    public DifferentialDriveWheelSpeeds getSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
    }

    @Override
    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(gyro.getAngle()); // Might need to negate angle
    }

    @Override
    public double getGyroRate() {
        return gyro.getRate();
    }

    // TODO: Configure Auton Builder
    /*
     * public void configureAutoBuilder() {
     * AutoBuilder.configure(
     * getPose(),
     * resetPose(),
     * null,
     * null,
     * null,
     * null,
     * null
     * );
     * }
     */

    @Override
    public double getLeftDistance() {
        return leftEncoder.getPosition() * Constants.Drivetrain.WHEEL_CIRCUMFERENCE_METERS * Constants.Drivetrain.DRIVETRAIN_GEAR_RATIO;
    }

    @Override
    public double getRightDistance() {
        return rightEncoder.getPosition() * Constants.Drivetrain.WHEEL_CIRCUMFERENCE_METERS * Constants.Drivetrain.DRIVETRAIN_GEAR_RATIO;
    }

    // Experimental, need confirmation that this is actually what sysId needs
    public LinearVelocity getMetersPerSecond(double velocity) {
        return MetersPerSecond.ofBaseUnits(velocity * Constants.Drivetrain.WHEEL_CIRCUMFERENCE_METERS / 60);
    }

    @Override
    public void resetPose() {
        odometry.resetPosition(gyro.getRotation2d(), getLeftDistance(), getRightDistance(), field.getRobotPose());
    }

    @Override
    public Pose2d getPose() {
        return odometry.getPoseMeters();
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
                                    .voltage(Voltage.ofBaseUnits(leftMotors[0].getBusVoltage(), Volts))
                                    .linearPosition(Meters.ofBaseUnits(getLeftDistance()))
                                    .linearVelocity(getMetersPerSecond(getLeftVelocity()));

                            log.motor("drive-right")
                                    .voltage(Voltage.ofBaseUnits(rightMotors[0].getBusVoltage(), Volts))
                                    .linearPosition(Meters.ofBaseUnits(getRightDistance()))
                                    .linearVelocity(getMetersPerSecond(getRightVelocity()));
                        },
                        this));
    }

    public Command getAutonomousCommand() {
        final DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
                ffController, kinematics, 10);

        final TrajectoryConfig trajectoryConfig = new TrajectoryConfig(Constants.Autonomous.MAX_SPEED_METERS_PER_SECOND,
                Constants.Autonomous.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
                .setKinematics(kinematics)
                .addConstraint(voltageConstraint);

        final Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, new Rotation2d(0)),                        // START
                List.of(new Translation2d(1, 1), new Translation2d(2, -1)),     // INTERMEDIATE SETPOINTS
                new Pose2d(3, 0, new Rotation2d()),                               // END
                trajectoryConfig);

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

    private void updateVision() { //TEMPORARY
        final double STEER_K = 0.03;
        final double DRIVE_K = 0.03;
        final double DESIRED_TARGET_AREA = 13.0;
        final double MAX_DRIVE = 0.3;

        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    
        if (tv<0) {
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
    public void periodic() {
        updateVision();
        updateOdometry();
    }
}
