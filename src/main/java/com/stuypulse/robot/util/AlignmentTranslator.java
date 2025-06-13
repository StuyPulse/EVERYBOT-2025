package com.stuypulse.robot.util;

import com.stuypulse.robot.constants.Field;
import com.stuypulse.robot.constants.Field.ReefTags;

public class AlignmentTranslator {
    public static String translate(String faceID, double direction) {
        String branch = null;

        int convertedDirection = convertDirection(direction);

        switch (faceID) {
            case "AB":
                branch = Integer.signum(convertedDirection) == -1 ? "A" : "B";
                break;

            case "CD":
                branch = Integer.signum(convertedDirection) == -1 ? "C" : "D";
                break;

            case "EF":
                branch = Integer.signum(convertedDirection) == -1 ? "E" : "F";
                break;

            case "GH":
                branch = Integer.signum(convertedDirection) == -1 ? "G" : "H";
                break;

            case "IJ":
                branch = Integer.signum(convertedDirection) == -1 ? "I" : "J";
                break;

            case "KL":
                branch = Integer.signum(convertedDirection) == -1 ? "K" : "L";
                break;
        }

        return branch;
    }

    public static String translate(ReefTags faceID, double direction) {
        String branch = null;
        
        int convertedDirection = convertDirection(direction);

        switch (faceID) {
            case BLUE_AB:
            branch = Integer.signum(convertedDirection) == -1 ? "A" : "B";
            break;

            case RED_AB:
            branch = Integer.signum(convertedDirection) == -1 ? "A" : "B";
            break;
            
            case BLUE_CD:
            branch = Integer.signum(convertedDirection) == -1 ? "C" : "D";
            break;

            case RED_CD:
            branch = Integer.signum(convertedDirection) == -1 ? "C" : "D";
            break;
            
            case BLUE_EF:
            branch = Integer.signum(convertedDirection) == -1 ? "E" : "F";
            break;

            case RED_EF:
            branch = Integer.signum(convertedDirection) == -1 ? "E" : "F";
            break;

            case BLUE_GH:
            branch = Integer.signum(convertedDirection) == -1 ? "G" : "H";
            break;
            
            case RED_GH:
            branch = Integer.signum(convertedDirection) == -1 ? "G" : "H";
            break;

            case BLUE_IJ:
            branch = Integer.signum(convertedDirection) == -1 ? "I" : "J";
            break;

            case RED_IJ:
            branch = Integer.signum(convertedDirection) == -1 ? "I" : "J";
            break;
            
            case BLUE_KL:
            branch = Integer.signum(convertedDirection) == -1 ? "K" : "L";
            break;

            case RED_KL:
            branch = Integer.signum(convertedDirection) == -1 ? "K" : "L";
            break;
        }
        
        return branch;
    }

    private static int convertDirection(double direction) {
        if (direction >= 0)
            return 1; // DEFAULTS TO RIGHT BRANCH ALIGNMENT
        return -1;
    }
}
