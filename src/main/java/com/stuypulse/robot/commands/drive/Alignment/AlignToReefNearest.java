package com.stuypulse.robot.commands.drive.Alignment;

import com.stuypulse.robot.constants.Field;
import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.subsystems.odometry.Odometry;
import com.stuypulse.robot.util.alignment.AlignmentTranslator;

public class AlignToReefNearest extends AlignToReef {
    public AlignToReefNearest(double direction) {
        super(Paths.paths.get(
            AlignmentTranslator.translate(
                Field.getClosestFace(Odometry.getInstance().getEstimatedPose()),
                direction)));
    }
}