package com.stuypulse.robot.commands.drive.Alignment;

import com.stuypulse.robot.constants.Field;
import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.subsystems.odometry.Odometry;
import com.stuypulse.robot.util.alignment.AlignmentTranslator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AlignToReefNearest extends AlignToReef {
    private Double direction = 0.0;

    public AlignToReefNearest(double direction) {
        super(Paths.paths.get(
            AlignmentTranslator.translate(
                Field.getClosestFace(Odometry.getInstance().getEstimatedPose()),
                direction)));

        this.direction = direction;
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Alignment/Selected Branch" + direction.toString(), AlignmentTranslator.translate(
            Field.getClosestFace(Odometry.getInstance().getEstimatedPose()),
            direction));
        SmartDashboard.putNumber("Alignment/Direction", direction);
    }
}