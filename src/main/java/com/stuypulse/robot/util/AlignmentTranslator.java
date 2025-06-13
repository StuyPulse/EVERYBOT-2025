package com.stuypulse.robot.util;

public class AlignmentTranslator {
    public static String translate(String face, double direction) {
        String branch = face;
        
        int convertedDirection = convertDirection(direction);
        
        switch (face) {
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
    
    private static int convertDirection(double direction) {
        if(direction>=0) return 1; //DEFAULTS TO RIGHT BRANCH ALIGNMENT
        return -1;
    }
}
