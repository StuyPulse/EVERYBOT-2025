package com.stuypulse.robot.commands.climb;

import com.stuypulse.robot.subsystems.climber.Climb.ClimbState;

public class ClimbToDeployed extends ClimbToState {
    public ClimbToDeployed() {
        super(ClimbState.DEPLOYED);
    }
}
