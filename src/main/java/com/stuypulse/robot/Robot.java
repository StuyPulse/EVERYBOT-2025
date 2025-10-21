/************************ PROJECT PHIL ************************/
/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot;

import com.stuypulse.robot.commands.pivot.pivot.PivotSetControlUsingStates;
import com.stuypulse.robot.commands.vision.VisionSetMegaTag1;
import com.stuypulse.robot.commands.vision.VisionSetMegaTag2;
import com.stuypulse.robot.util.Clearances;

import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private RobotContainer robot;
    private Command auto;
    private Timer timer;

    /*************************/
    /*** ROBOT SCHEDULEING ***/
    /*************************/

    @Override
    public void robotInit() {
        DataLogManager.start();
        DriverStation.startDataLog(DataLogManager.getLog());
        robot = new RobotContainer();
        new VisionSetMegaTag1();

        // Testing for Port Forwarding

        //Try port 57074 etc... with its respective ip (**netstat -a**)
        for (int LMport = 5801; LMport<= 5810; LMport ++){
             for (int SCport = 4901; SCport <= 4910; SCport ++) {

        // for (int LMport = 5801; LMport<= 5810; LMport ++){
        //     for (int SCport = 4901; SCport <= 4910; SCport ++) {

        // for (int LMport = 5801; LMport<= 5810; LMport ++){
        //     for (int SCport = 4901; SCport <= 4910; SCport ++) {

                 PortForwarder.add(SCport, "10.5.16.11", LMport);
                 PortForwarder.add(LMport, "10.5.16.11", SCport);
                 PortForwarder.add(SCport, "10.5.16.11", SCport);
                 PortForwarder.add(LMport, "10.5.16.11", LMport);

                 PortForwarder.add(LMport, "10.5.16.4", SCport);
                 PortForwarder.add(SCport, "10.5.16.4", LMport);
                 PortForwarder.add(LMport, "10.5.16.4", LMport);
                 PortForwarder.add(SCport, "10.5.16.4", SCport);


                  PortForwarder.add(SCport, "172.28.0.1", LMport);
                  PortForwarder.add(LMport, "172.28.0.1", SCport);
                  PortForwarder.add(SCport, "172.28.0.1", SCport);
                  PortForwarder.add(LMport, "172.28.0.1", LMport);

                  PortForwarder.add(SCport + 10, "10.5.16.11", LMport);
                 PortForwarder.add(LMport + 10, "10.5.16.11", SCport);
                 PortForwarder.add(SCport + 10, "10.5.16.11", SCport);
                 PortForwarder.add(LMport + 10, "10.5.16.11", LMport);

                 PortForwarder.add(LMport + 10, "10.5.16.4", SCport);
                 PortForwarder.add(SCport + 10, "10.5.16.4", LMport);
                 PortForwarder.add(LMport + 10, "10.5.16.4", LMport);
                 PortForwarder.add(SCport + 10, "10.5.16.4", SCport);


                  PortForwarder.add(SCport + 10, "172.28.0.1", LMport);
                  PortForwarder.add(LMport + 10, "172.28.0.1", SCport);
                  PortForwarder.add(SCport + 10, "172.28.0.1", SCport);
                  PortForwarder.add(LMport + 10, "172.28.0.1", LMport);
                
                  PortForwarder.add(SCport, "", LMport);
                  PortForwarder.add(LMport, "", SCport);
                  PortForwarder.add(SCport, "", SCport);
                  PortForwarder.add(LMport, "", SCport);
                  
                  
                  PortForwarder.add(LMport, "", SCport);
                  PortForwarder.add(SCport, "", LMport);
                  PortForwarder.add(LMport, "", LMport);
                  PortForwarder.add(SCport, "", SCport);
 
 
                   PortForwarder.add(SCport, "", LMport);
                   PortForwarder.add(LMport, "", SCport);
                   PortForwarder.add(SCport, "", SCport);
                   PortForwarder.add(LMport, "", LMport);

                  
                  //TODO:
                  //TEST IPS with COLONS
                  //TEST ALL IPS IN NETSAT WHEN TETHERED INTO BOTH LIMELIGHT OR SYSTEM CORE
                  //TEST DIFFERENT PORTS
                  //TEST PORTS AS IP WITH PORT NUMBERS (IP:PORT)
                  
                  
                 
                
                //Add more to Portforwarder with different combinations and find IPs
                // figure out how to display the port that works in Smart Dashboard
                //Maybe do 4901 only in for loop like the 5801 only above
            }
        } 
       
        timer = new Timer();
        timer.reset();
        timer.start();
    }

    @Override
    public void robotPeriodic() {
        timer.reset();
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("DriverStation/Match Time", DriverStation.getMatchTime());
        PortForwarder.add(5801, "10.99.99.2", 5801);
        
        //Clearances
        SmartDashboard.putBoolean("Clearances/From Reef", Clearances.isClearFromReef());
        SmartDashboard.putBoolean("Clearances/From Proc", Clearances.isClearFromProc());
        SmartDashboard.putData("Command Scheduler", CommandScheduler.getInstance());
        SmartDashboard.putNumber("Periodic/Cycle time", timer.get());
    }

    /*********************/
    /*** DISABLED MODE ***/
    /*********************/

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {
        if (DriverStation.isFMSAttached()) {
            CommandScheduler.getInstance().schedule(new VisionSetMegaTag2());
        }
    }

    /***********************/
    /*** AUTONOMOUS MODE ***/
    /***********************/  

    @Override
    public void autonomousInit() {
        auto = robot.getAutonomousCommand();
        
        if (auto != null) {
            CommandScheduler.getInstance().schedule(new PivotSetControlUsingStates());
            CommandScheduler.getInstance().schedule(auto);
        }

    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    /*******************/
    /*** TELEOP MODE ***/
    /*******************/

    @Override
    public void teleopInit() {
        if (auto != null) {
            auto.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void teleopExit() {}

    /*****************/
    /*** TEST MODE ***/
    /*****************/

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}
}
