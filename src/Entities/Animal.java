package Entities;

import Buildings.Position;
import Buildings.Street;
import Enums.Color;
import Talks.Talk;

public class Animal extends LivingEntity {

    private Color woolColor;
    private boolean isWool;
    private int currentTalkID;

    public Animal(String name, Color eyeColor, int legsNum, int armsNum,
                  int headNum, boolean isMan, int years, boolean isWool,
                  Color woolColor) {
        super(name, eyeColor, legsNum, armsNum, headNum, isMan, years);

        this.isWool = isWool;
        this.woolColor = woolColor;
    }

    public Color getWoolColor() {
        if (isWool)
            return woolColor;
        return null;
    }

    public boolean isWool() {
        return isWool;
    }

    public Talk getCurrentTalk() {
        return this.getPosition().getStreet().getTalk(currentTalkID);
    }

    @Override
    public String toString() {
        return getName();
    }
    @Override
    public int hashCode() {
        int prime = 4;
        int result = 1;
        int iswool = isWool ? 1 + woolColor.toString().length() : 0;
        result = prime * result + super.hashCode() + currentTalkID + iswool;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Animal other = (Animal) obj;
        if (!getName().equals(other.getName()))
            return false;
        if (getEyeColor() != other.getEyeColor())
            return false;
        if (getLegsNum() != other.getLegsNum())
            return false;
        if (getArmsNum() != other.getArmsNum())
            return false;
        if (getHeadNum() != other.getHeadNum())
            return false;
        if (isMan() != other.isMan())
            return false;
        if (getYears() != other.getYears())
            return false;
        if (!getPosition().equals(other.getPosition()))
            return false;
        if (!getMemory().equals(other.getMemory()))
            return false;
        if (isWool != other.isWool)
            return false;
        if(isWool) {
            if (woolColor != other.woolColor)
                return false;
        }
        return true;
    }
}
