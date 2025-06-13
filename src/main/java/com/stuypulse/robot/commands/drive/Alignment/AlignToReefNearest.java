package com.stuypulse.robot.commands.drive.Alignment;

import com.stuypulse.robot.constants.Field;
import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.subsystems.vision.LimelightVision;
import com.stuypulse.robot.util.AlignmentTranslator;

public class AlignToReefNearest extends AlignToReef {
    public AlignToReefNearest(double direction) {
        super(Paths.paths.get(AlignmentTranslator.translate(Field.getClosestFace(LimelightVision.getInstance().getEstimatedPose()), direction)));
    }
}