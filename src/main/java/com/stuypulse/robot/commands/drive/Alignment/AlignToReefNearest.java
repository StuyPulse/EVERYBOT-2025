package com.stuypulse.robot.commands.drive.Alignment;

import com.stuypulse.robot.constants.Field;
import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.subsystems.odometry.Odometry;
import com.stuypulse.robot.util.alignment.AlignmentTranslator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AlignToReefNearest extends AlignToReef {
    private boolean alignToLeftBranch;

    public AlignToReefNearest(Boolean alignToLeftBranch) {
        super(Paths.paths.get(
                AlignmentTranslator.translate(
                        Field.getClosestFace(() -> Odometry.getInstance().getEstimatedPose()),
                        alignToLeftBranch)));
        this.alignToLeftBranch = alignToLeftBranch;
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Alignment/Selected Branch" + alignToLeftBranch, AlignmentTranslator.translate(
                Field.getClosestFace(() -> Odometry.getInstance().getEstimatedPose()),
                alignToLeftBranch));
        SmartDashboard.putString("Alignment/Selected Path" + alignToLeftBranch, Paths.paths.get(
            AlignmentTranslator.translate(
                    Field.getClosestFace(() -> Odometry.getInstance().getEstimatedPose()),
                    alignToLeftBranch)).name);
        SmartDashboard.putBoolean("Alignment/Is left branch", alignToLeftBranch);
        SmartDashboard.putNumber("Alignment/closest id ", Field.getClosestFace(() -> Odometry.getInstance().getEstimatedPose()).getID());
    }
}