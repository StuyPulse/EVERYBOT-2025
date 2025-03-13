package com.stuypulse.robot.commands.leds;
import java.util.Optional;

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
        Optional<Alliance> ally = DriverStation.getAlliance();
        if (ally.isPresent()) {
            if (ally.get() == Alliance.Red) {
                leds.applyPattern(LEDPattern.solid(Color.kRed));
            }
            if (ally.get() == Alliance.Blue) {
                leds.applyPattern(LEDPattern.solid(Color.kBlue));
            }
        }
        else {
           leds.applyPattern(LEDPattern.solid(Color.kGreen));
        
        }
    }
}
