package Entities;

import Buildings.Building;
import Buildings.Position;
import Buildings.Street;
import Enums.*;
import MainPkg.Main;
import Mind.Memory;
import Mind.Thought;
import Talks.Talk;
import Talks.Talkative;

public abstract class LivingEntity {
    private String name;
    private Color eyeColor;
    private int legsNum;
    private int armsNum;
    private int headNum;
    private boolean isMan;
    private int years;
    private Position position;
    private int currentTalkID;
    private Memory memory;

    public LivingEntity(String name, Color eyeColor, int legsNum, int armsNum,
                        int headNum, boolean isMan, int years){
        this.name = name;
        this.eyeColor =  eyeColor;
        this.legsNum =  legsNum;
        this.armsNum =  armsNum;
        this.headNum = headNum;
        this.isMan = isMan;
        this.years = years;
        this.memory = new Memory();
        this.position = new Position(this);
    }

    public String getName() {
        return name;
    }

    public boolean isMan() {
        return isMan;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public int getLegsNum() {
        return legsNum;
    }

    public int getArmsNum() {
        return armsNum;
    }

    public int getHeadNum() {
        return headNum;
    }

    protected Memory getMemory() {
        return memory;
    }

    public int getYears() {
        return years;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Thought recallSmth(){
        return this.memory.getRandomThought();
    }

    public void goForward(){
        if(this.getPosition().getStreetPos() != this.getPosition().getStreet().getStreetBuildings().size()-1) {
            if (this.getPosition().isOutdoor()) {
                this.getPosition().setStreetPos(this.getPosition().getStreetPos() + 1);
                Main.pause("Персонаж " + this.toString() + " шагнул вперед.");
            } else {
                Main.pause("Персонаж " + this.toString() + " не может пойти вперёд, так как он в здании.");
            }
        }
        else{
            Main.pause("Персонаж " + this.toString() + " достиг конца улицы.");
        }
    }

    public void goBack(){
        if(this.getPosition().getStreetPos() != 0) {
            if (this.getPosition().isOutdoor()) {
                this.getPosition().setStreetPos(this.getPosition().getStreetPos() - 1);
                Main.pause("Персонаж " + this.toString() + " шагнул назад.");
            } else {
                Main.pause("Персонаж " + this.toString() + " не может пойти назад, так как он в здании.");
            }
        }
        else{
            Main.pause("Персонаж " + this.toString() + " достиг начала улицы.");
        }
    }

    public void setCurrentTalk(Talk talk) {
        if (this.getPosition().getStreet().getTalk(talk.getTalk_id()) != null) {
            currentTalkID = talk.getTalk_id();
        }
    }

    public void exploreStreet(){
        Building building = this.getPosition().getStreet().getStreetBuildings().get(this.getPosition().getStreetPos());
        Main.pause(String.format("Персонаж %s обследовал часть улицы.", this.getName()));
        memory.remember(building);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        int prime = 6;
        int isman = isMan ? 1 : 0;
        int result = 1;
        result = result * prime + name.length() + eyeColor.toString().length() +
                legsNum + armsNum + headNum + isman + years + position.hashCode() +
                currentTalkID + memory.hashCode();
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
        LivingEntity other = (LivingEntity) obj;
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
        return true;
    }
}
