package com.stuypulse.robot.util.alignment;

import com.stuypulse.robot.constants.Field.ReefTags;

public class AlignmentTranslator {
    private static boolean alignToLeftBranchint = false;
    public static String translate(String faceID, Boolean alignToLeftBranch) {
        String branch = null;
        alignToLeftBranchint = alignToLeftBranch;
        switch (faceID) {
            case "AB":
                branch = alignToLeftBranch ? "A" : "B";
                break;

            case "CD":
                branch = alignToLeftBranch ? "C" : "D";
                break;

            case "EF":
                branch = alignToLeftBranch ? "E" : "F";
                break;

            case "GH":
                branch = alignToLeftBranch ? "G" : "H";
                break;

            case "IJ":
                branch = alignToLeftBranch ? "I" : "J";
                break;

            case "KL":
                branch = alignToLeftBranch ? "K" : "L";
                break;
        }

        return branch;
    }

    public static String translate(ReefTags faceID, boolean alignToLeftBranch) {
        String branch = null;
        alignToLeftBranchint = alignToLeftBranch; 

        switch (faceID) {
            case BLUE_AB:
            branch = alignToLeftBranchint ? "A" : "B";
            break;

            case RED_AB:
            branch = alignToLeftBranchint ? "A" : "B";
            break;
            
            case BLUE_CD:
            branch = alignToLeftBranchint ? "C" : "D";
            break;

            case RED_CD:
            branch = alignToLeftBranchint ? "C" : "D";
            break;
            
            case BLUE_EF:
            branch = alignToLeftBranchint ? "E" : "F";
            break;

            case RED_EF:
            branch = alignToLeftBranchint ? "E" : "F";
            break;

            case BLUE_GH:
            branch = alignToLeftBranchint ? "G" : "H";
            break;
            
            case RED_GH:
            branch = alignToLeftBranchint ? "G" : "H";
            break;

            case BLUE_IJ:
            branch = alignToLeftBranchint ? "I" : "J";
            break;

            case RED_IJ:
            branch = alignToLeftBranchint ? "I" : "J";
            break;
            
            case BLUE_KL:
            branch = alignToLeftBranchint ? "K" : "L";
            break;

            case RED_KL:
            branch = alignToLeftBranchint ? "K" : "L";
            break;
        }
        
        return branch;
    }
}
