package com.stuypulse.robot.commands.drive.Alignment;

import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.util.alignment.AlignmentTranslator;

public class AlignToReefCD extends AlignToReef{
    public AlignToReefCD(double direction) {
        super(Paths.paths.get(AlignmentTranslator.translate("CD", direction)));
    }
}
