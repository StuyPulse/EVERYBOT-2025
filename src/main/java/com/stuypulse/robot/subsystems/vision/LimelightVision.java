package com.stuypulse.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class LimelightVision extends SubsystemBase {
    public static final LimelightVision instance;
    
    static {
        instance = new LimelightVisionImpl();
    }

    public static LimelightVision getInstance() {
        return instance;
    }
    
    public enum MegaTagMode {
        MEGATAG1,
        MEGATAG2
    }

    public abstract void SetMegaTagMode(MegaTagMode mtMode);
}
