package com.stuypulse.robot.util;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
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
                log -> { log.motor(motorname).angularPosition(mechgetrotations.getMeasure());},
                subsysteminstance
            )
        );
    }
}


