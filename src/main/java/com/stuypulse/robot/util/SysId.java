package com.stuypulse.robot.util;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class SysId {
    public static SysIdRoutine getSysIdRoutine(
        String motorname,
        SparkMax motor,
        Rotation2d mechgetrotations,
        Subsystem subsysteminstance
    )
    {
        return new SysIdRoutine(
            new SysIdRoutine.Config(),
            new SysIdRoutine.Mechanism(
                motor::setVoltage,
                log -> { log.motor(motorname)
                    .angularVelocity(AngularVelocity.ofBaseUnits(motor.getEncoder().getVelocity(), RotationsPerSecond))
                    .voltage(Voltage.ofBaseUnits(motor.getBusVoltage(), Volts))
                    .angularPosition(mechgetrotations.getMeasure());
                },
                subsysteminstance
            )
        );
    }
}