package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.stuylib.control.feedforward.MotorFeedforward;
import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Gains;
import com.stuypulse.robot.constants.Motors.DrivetrainConfig;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

    private final Field2d field = new Field2d();
    
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

        /* Back wheel config:
        *  back left will follow front left, safe parameters will persist; config will
        *  persist across power cycles */

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

        // Left Lead
        leftEncoder = leftMotors[0].getEncoder();
        // Right Lead
        rightEncoder = rightMotors[0].getEncoder();

        //Odometry + Controllers
        odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0);

        controllerPosition = new MotorFeedforward(Gains.Drivetrain.FF.kS, Gains.Drivetrain.FF.kV, Gains.Drivetrain.FF.kA)
                .position();
        
        DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG.inverted(true); 
        leftMotors[0].configure(DrivetrainConfig.DRIVETRAIN_MOTOR_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        leftMotors[0].setCANTimeout(250);
        leftMotors[1].setCANTimeout(250);
        rightMotors[0].setCANTimeout(250);
        rightMotors[1].setCANTimeout(250);

        SmartDashboard.putData("Field", field);
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

    private void updateOdometry() {
        odometry.update(gyro.getRotation2d(), leftEncoder.getPosition(), rightEncoder.getPosition());
    }

    @Override
    public double getLeftVelocity() {
        return leftEncoder.getVelocity();
    }

    @Override
    public double getRightVelocity() {
        return rightEncoder.getVelocity();
    }

    //TODO: Configure Auton Builder
    /*public void configureAutoBuilder() {
        AutoBuilder.configure(
            getPose(), 
            resetPose(), 
            null,
            null,
            null,
            null, 
            null
        );
    }*/

    public double getLeftDistance() {
        return leftEncoder.getPosition() * Constants.Drivetrain.WHEEL_CIRCUMFERENCE;
    }

    public double getRightDistance() {
        return rightEncoder.getPosition() * Constants.Drivetrain.WHEEL_CIRCUMFERENCE;
    }

    // Experimental, need confirmation that this is actually what sysId needs
    public LinearVelocity getMetersPerSecond(double velocity){
        return MetersPerSecond.ofBaseUnits(velocity * Constants.Drivetrain.WHEEL_CIRCUMFERENCE / 60); 
    }

    public void resetPose() {
        odometry.resetPosition(gyro.getRotation2d(), getLeftDistance(), getRightDistance(), field.getRobotPose());
    }

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
                this     
            )
        );
    }

    @Override
    public void periodic() {
        updateOdometry();
        field.setRobotPose(odometry.getPoseMeters());
    }
}
