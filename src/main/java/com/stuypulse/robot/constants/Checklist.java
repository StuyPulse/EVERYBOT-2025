package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.network.SmartBoolean;

public interface Checklist {
    public static final SmartBoolean FMS_CONNECTED = new SmartBoolean("Checklist/Is The FMS Connected?", false);
    public static final SmartBoolean ROBOT_CONNECTED = new SmartBoolean("Checklist/Is The DS Connected to the Robot?", false);
    public static final SmartBoolean TAG_ROBOT = new SmartBoolean("Checklist/Is The Robot Tagged?", false);
    public static final SmartBoolean SELECT_AUTON = new SmartBoolean("Checklist/Is The Correct Auton Selected?", false);
    public static final SmartBoolean POSITION_ROBOT = new SmartBoolean("Checklist/Is The Robot in The Correct Auton Position?", false);
    public static final SmartBoolean PRELOAD_ROBOT = new SmartBoolean("Checklist/Is Robot Preloaded?", false);
}
