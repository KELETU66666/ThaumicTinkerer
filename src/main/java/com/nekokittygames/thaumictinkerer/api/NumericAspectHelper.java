package com.nekokittygames.thaumictinkerer.api;

import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;

public class NumericAspectHelper {

    public static ArrayList<NumericAspectHelper> values = new ArrayList<>();
    private static int nextNum = 0;
    public int num;
    private Aspect aspect;

    NumericAspectHelper(Aspect aspect) {
        this.aspect = aspect;
        this.num = nextNum;
        nextNum++;
    }

    static{
        values.add(new NumericAspectHelper(Aspect.WATER));
        values.add(new NumericAspectHelper(Aspect.MAN));
        values.add(new NumericAspectHelper(Aspect.AIR));
        values.add(new NumericAspectHelper(Aspect.FLIGHT));
        values.add(new NumericAspectHelper(Aspect.FIRE));
        values.add(new NumericAspectHelper(Aspect.MAGIC));
        values.add(new NumericAspectHelper(Aspect.UNDEAD));
        values.add(new NumericAspectHelper(Aspect.DESIRE));
        values.add(new NumericAspectHelper(Aspect.BEAST));
        values.add(new NumericAspectHelper(Aspect.DEATH));
        values.add(new NumericAspectHelper(Aspect.EARTH));
        values.add(new NumericAspectHelper(Aspect.ELDRITCH));
        values.add(new NumericAspectHelper(Aspect.MOTION));
        values.add(new NumericAspectHelper(Aspect.METAL));
        values.add(new NumericAspectHelper(Aspect.ALCHEMY));
        values.add(new NumericAspectHelper(Aspect.CRAFT));
        values.add(new NumericAspectHelper(Aspect.PLANT));
    }

    public static int getNumber(Aspect aspect) {
        for (NumericAspectHelper e : NumericAspectHelper.values) {
            if (e.getAspect().equals(aspect)) {
                return e.num;
            }
        }

        return -1;
    }

    public static Aspect getAspect(int i) {
        return NumericAspectHelper.values.get(i).getAspect();
    }

    public Aspect getAspect() {
        return aspect;
    }

}