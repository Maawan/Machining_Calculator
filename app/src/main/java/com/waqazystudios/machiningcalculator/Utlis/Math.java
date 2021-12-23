package com.waqazystudios.machiningcalculator.Utlis;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Math {
    public static final String machinedDiameter = "MACHINED DIAMETER (Dm)";
    public static final String spindleSpeed = "SPINDLE SPEED (n)";
    public static final String cuttingSpeed = "CUTTING SPEED (VC)";
    public static final String metalRemovalRate = "METAL REMOVAL RATE (Q)";
    public static final String feedPerRevolution = "FEED PER REVOLUTION";
    public static final String depthOfCut = "DEPTH OF CUT";
    public static final String specificCuttingForce = "SPECIFIC CUTTING FORCE";
    public static final String lengthOfCut = "LENGTH OF CUT";
    public static final String radialWidthOfCut = "RADIAL WIDTH OF CUT (AE)";
    public static final String feedPerTooth = "FEED PER TOOTH (FZ)";
    public static final String numberOfInserts = "NUMBER OF INSERTS (ZC)";
    public static final String feedSpeed = "FEED SPEED (VF)";
    public static final String timeInCut = "TIME IN CUT";
    public static final String powerRequirement = "POWER REQUIREMENT (Pc)";
    public static final String historyData = "HISTORY DATA";
    public static int adCount = 0;

    public Math(){

    }
    public static double getRoundOff(float value , int places){
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }
}
