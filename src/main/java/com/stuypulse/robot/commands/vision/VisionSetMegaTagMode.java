package com.stuypulse.robot.commands.vision;

import com.stuypulse.robot.subsystems.vision.LimelightVision;
import com.stuypulse.robot.subsystems.vision.LimelightVision.MegaTagMode;

import edu.wpi.first.wpilibj2.command.Command;

public class VisionSetMegaTagMode extends Command {
    private final LimelightVision vision = LimelightVision.getInstance();
    private final MegaTagMode megaTagMode;

    public VisionSetMegaTagMode(MegaTagMode megaTagMode) {
        this.megaTagMode = megaTagMode;

        addRequirements(vision);
    }

    @Override 
    public void initialize() {
        vision.SetMegaTagMode(megaTagMode);
    }
}
