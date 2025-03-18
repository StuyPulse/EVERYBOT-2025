/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot;

import com.stuypulse.robot.commands.auton.DoNothingAuton;
import com.stuypulse.robot.commands.auton.DoubleL1Auton;
import com.stuypulse.robot.commands.auton.MobilityAuton;
import com.stuypulse.robot.commands.auton.SingleL1Auton;
import com.stuypulse.robot.commands.auton.PushBackwardsL1Auton;
import com.stuypulse.robot.commands.climb.ClimbToClimb;
import com.stuypulse.robot.commands.climb.ClimbToStow;
import com.stuypulse.robot.commands.drive.DriveDefault;
import com.stuypulse.robot.commands.leds.LEDDeafultCommand;
import com.stuypulse.robot.commands.pivot.PivotAlgaeIntake;
import com.stuypulse.robot.commands.pivot.PivotAlgaeOutake;
import com.stuypulse.robot.commands.pivot.PivotCoralOut;
import com.stuypulse.robot.commands.pivot.PivotLower;
import com.stuypulse.robot.commands.pivot.PivotRaise;
import com.stuypulse.robot.commands.pivot.PivotStop;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.climber.Climb;
import com.stuypulse.robot.subsystems.climber.ClimbImpl;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.leds.LEDController;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.PivotImpl;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

    // Gamepads
    public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
    public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);
    
    // Subsystem
    private final LEDController ledSubsystem = LEDController.getInstance();
    private final Drivetrain driveSubsystem = Drivetrain.getInstance();
    private final Climb climbSubsystem = Climb.getInstance();
    private final Pivot pivotSubsystem = Pivot.getInstance();

    // Autons
    private static SendableChooser<Command> autonChooser = new SendableChooser<>();

    // Robot container

    public RobotContainer() {
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
    }

    /****************/
    /*** DEFAULTS ***/
    /****************/

    private void configureDefaultCommands() {
        ledSubsystem.setDefaultCommand(new LEDDeafultCommand());
        driveSubsystem.setDefaultCommand(new DriveDefault(driver, true));
    }

    /***************/
    /*** BUTTONS ***/
    /***************/

    private void configureButtonBindings() {
        driver.getTopButton()
            .whileTrue(new PivotCoralOut());
        driver.getLeftButton()
            .onTrue(new ClimbToClimb());
        driver.getRightButton()
            .onTrue(new ClimbToStow());
        // driver.getBottomButton()
        //     .onTrue(new PivotStop());

        driver.getRightTriggerButton()
            .whileTrue(new PivotRaise())
            .onFalse(new PivotStop());
        driver.getLeftTriggerButton()
            .whileTrue(new PivotLower())
            .onFalse(new PivotStop());

        driver.getRightBumper() 
            .whileTrue(new PivotAlgaeOutake());
        driver.getLeftBumper()
            .whileTrue(new PivotAlgaeIntake());

    }
        

    /**************/
    /*** AUTONS ***/
    /**************/

    public void configureAutons() {

        autonChooser.setDefaultOption("Mobility Auton", new MobilityAuton());
        autonChooser.addOption("Single L1", new SingleL1Auton());
        autonChooser.addOption("Double L1", new DoubleL1Auton());
        autonChooser.addOption("Push Backwards", new PushBackwardsL1Auton());
        autonChooser.addOption("Do Nothing", new DoNothingAuton());

        SmartDashboard.putData("Autonomous", autonChooser);
    }

    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
    }
}
