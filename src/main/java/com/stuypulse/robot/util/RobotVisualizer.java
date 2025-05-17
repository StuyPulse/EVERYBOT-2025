package com.stuypulse.robot.util;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotControlMode;

public class RobotVisualizer {
    
    public static RobotVisualizer instance;

    static {
        instance = new RobotVisualizer();
    }

    public static RobotVisualizer getInstance() {
        return instance;
    }

    private final Mechanism2d canvas;
    private double width, height;

    private final MechanismRoot2d bumper;
    private final MechanismLigament2d bumperLine;
    private final MechanismRoot2d superStructureBase;
    private final MechanismLigament2d superStructureLength;
    private final MechanismLigament2d pivot;
    private final MechanismRoot2d pivotVertex;
    private final MechanismRoot2d pivotSupportEnd;
    private final MechanismLigament2d pivotSupport;

    private double pivotAngle;

    private final MechanismRoot2d rollerRoot;
    private final MechanismLigament2d[] rollerLigaments;

    private double rollerSpeed;

    private RobotVisualizer() {
        width = 150;
        height = 100;

        canvas = new Mechanism2d(width, height, new Color8Bit(Color.kSlateGray));

        /* Bumper */
        bumper = canvas.getRoot("c - Bumper", 60, 10);

        bumperLine = new MechanismLigament2d(
            "c - Bumper Line",
            80,
            0,
            75,
            new Color8Bit(Color.kBlue)
        );

        /* Pivot and SuperStructure */
        superStructureBase = canvas.getRoot("b - Super Structure Base", 80, 10);
        
        superStructureLength = new MechanismLigament2d(
            "b - Super Structure",
            65,
            90,
            15,
            new Color8Bit(Color.kLightGray)
        );

        pivotAngle = 90+Settings.Pivot.DEFAULT_ANGLE.getDegrees();
        
        pivotVertex = canvas.getRoot("a - Pivot Rotation Vertex", 70, 25);
        
        pivot = new MechanismLigament2d(
            "a - Pivot",
            40,
            pivotAngle,
            15,
            new Color8Bit(Color.kGreen)
        );

        pivotSupportEnd = canvas.getRoot("d - Pivot Support Vertex", 68, 24);
        
        pivotSupport = new MechanismLigament2d(
            "d - Pivot Support",
            15,
            0,
            30,
            new Color8Bit(Color.kDarkGray)
        );


        /* Rollers */
        rollerRoot = canvas.getRoot("z - Roller Root", 70, 65);
        rollerLigaments = new MechanismLigament2d[4];
        for(int i = 0; i < 4; i++){
            rollerLigaments[i] = new MechanismLigament2d(
                "z - Roller " + (i+1), 
                4, 
                90 * i,
                6,
                new Color8Bit(Color.kBlack)
            );
            rollerRoot.append(rollerLigaments[i]);
        }

        /* Appending Stuff */
        bumper.append(bumperLine);
        superStructureBase.append(superStructureLength);
        pivotVertex.append(pivot);
        pivotSupportEnd.append(pivotSupport);

    }

    public void updatePivotAngle(Rotation2d angle, boolean atTargetAngle) {
        this.pivotAngle = 90+angle.getDegrees();
        pivot.setAngle(pivotAngle); 
        if(Pivot.getInstance().PivotControlMode() == PivotControlMode.MANUAL){
            pivot.setColor(new Color8Bit(Color.kViolet));
        } else if(Pivot.getInstance().PivotControlMode() == PivotControlMode.USING_STATES) {
            pivot.setColor(atTargetAngle ? new Color8Bit(Color.kGreen) : new Color8Bit(Color.kRed));
        }

        rollerRoot.setPosition(
            70 + (Math.cos(Units.degreesToRadians(pivotAngle)) * 40),
            25 + (Math.sin(Units.degreesToRadians(pivotAngle)) * 40)
        );

        SmartDashboard.putData("Visualizers/Robot", canvas);
    }

    public void updateRollers(double speed) {
        this.rollerSpeed = speed;
        for(int i = 0; i < 4; i++) {
            rollerLigaments[i].setAngle(rollerLigaments[i].getAngle() + (-rollerSpeed) * 20);
        }
        
        SmartDashboard.putData("Visualizers/Robot", canvas);
    }

}
