package com.stuypulse.robot.commands.leds;
import java.util.Optional;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.LEDPatterns;
import com.stuypulse.robot.subsystems.leds.LEDController;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

public class LEDDeafultCommand extends Command{
    private final LEDController leds;
    
    
    public LEDDeafultCommand() {
        this.leds = LEDController.getInstance();
        // TODO: When we merge, add other subsystems here
        addRequirements(leds);
    }
    
    @Override
    public void execute() {
        // TODO: check for robot states here and assign led patterns to them
        Optional<Alliance> ally = DriverStation.getAlliance();
        if (ally.isPresent()) {
            if (ally.get() == Alliance.Red) {
                leds.applyPattern(Settings.LEDPatterns.RED_ALLIANCE);
            }
            if (ally.get() == Alliance.Blue) {
                leds.applyPattern(Settings.LEDPatterns.BLUE_ALLIANCE);
            }
        }
        else {
           leds.applyPattern(Settings.LEDPatterns.NO_ALLIANCE);
        }
    }
}
