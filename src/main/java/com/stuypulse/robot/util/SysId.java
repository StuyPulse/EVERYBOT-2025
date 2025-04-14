package com.stuypulse.robot.util;

import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Rotations;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class SysId {
    public static SysIdRoutine getSysIdRoutine(
        String motorname,
        SparkMax motor,
        Rotation2d mechGetRotations,
        Subsystem subsysteminstance,
        double quasistaticRampVoltage,  //1V by default
        double dynamicStepVoltage,      //7V by default
        double timeoutSec               //10s by default
    )
    {
        return new SysIdRoutine(
            new SysIdRoutine.Config(
                Voltage.ofBaseUnits(quasistaticRampVoltage, Volts).per(Seconds),
                Voltage.ofBaseUnits(dynamicStepVoltage, Volts), 
                Time.ofBaseUnits(timeoutSec, Seconds)),
            new SysIdRoutine.Mechanism(
                motor::setVoltage,
                log -> { log.motor(motorname)
                    .angularVelocity(AngularVelocity.ofBaseUnits(motor.getEncoder().getVelocity() / 60, RotationsPerSecond))
                    .voltage(Voltage.ofBaseUnits(motor.getBusVoltage(), Volts))
                    .angularPosition((Angle.ofBaseUnits(motor.getEncoder().getPosition(), Rotations)));
                },
                subsysteminstance
            )
        );
    }
}