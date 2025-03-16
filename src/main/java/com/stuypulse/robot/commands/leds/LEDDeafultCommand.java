package com.stuypulse.robot.commands.leds;
import java.util.Optional;

import com.stuypulse.robot.constants.Settings.LEDPatterns;
import com.stuypulse.robot.subsystems.climber.Climb;
import com.stuypulse.robot.subsystems.climber.Climb.ClimbState;
import com.stuypulse.robot.subsystems.leds.LEDController;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;

public class LEDDeafultCommand extends Command{
    private final LEDController leds;
    private final Climb climb;
    
    public LEDDeafultCommand() {
        this.leds = LEDController.getInstance();
        this.climb = Climb.getInstance();
        addRequirements(leds);
    }
    
    @Override
    public void execute() {
        // TODO: check for robot states here and assign led patterns to them
        Optional<Alliance> ally = DriverStation.getAlliance();
        if (climb.getState() == ClimbState.CLIMBING) {
            leds.applyPattern(LEDPatterns.CLIMBING);
        }
        else if (ally.isPresent()) {
            if (ally.get() == Alliance.Red) {
                leds.applyPattern(LEDPatterns.RED_ALLIANCE);
            }
            if (ally.get() == Alliance.Blue) {
                leds.applyPattern(LEDPatterns.BLUE_ALLIANCE);
            }
        } 
        else {
           leds.applyPattern(LEDPatterns.NO_ALLIANCE);
        }
    }
}
