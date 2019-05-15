package Entities;

import Enums.*;
import Finances.Wallet;
import MainPkg.Main;
import Talks.PaperMessage;
import Talks.Talk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Human extends LivingEntity {

    private Color hairColor;
    private BodyType bodyType;
    private int currentTalkID;
    private Wallet wallet;
    //Changed for 4 Lab
    private ArrayList<PaperMessage> pocket;

    public ArrayList<PaperMessage> getPocketContent(){
        return pocket;
    }

    public Human(String name, Color eyeColor, int legsNum, int armsNum, int headNum,
                 boolean isMan, int years, Color hairColor, BodyType bodyType,
                 Wallet wallet) {
        super(name, eyeColor, legsNum, armsNum, headNum, isMan, years);
        try {
            if(name.length() == 0)
                throw new NameException(this);
        } catch (NameException e) {
            System.err.println("The name of " + this.getClass().getName() + " is empty!!!");
            System.exit(1);
        }

        pocket = new ArrayList<>();
        this.hairColor = hairColor;
        this.bodyType = bodyType;
        this.wallet = wallet;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public Talk getCurrentTalk() {
        return this.getPosition().getStreet().getTalk(currentTalkID);
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void playLottery(){
        int first_border = 1;
        int end_border = 10;
        int random = first_border + (int) (Math.random() *
                ((end_border - first_border) + 1));

        Method method = null;
        try {
            method = wallet.getCurrencyType().getMethod("getRate");

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        int rate = 0;
        try {
            rate = (int) method.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        wallet.takeMoney(50/rate);

        if (random == 5){
            try {
                wallet.addMoney(wallet.getCurrencyType().getConstructor(int.class)
                        .newInstance(1000000/rate));
            } catch (InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException e) {
                e.printStackTrace();
            }
            Main.pause("Персонаж " + this.toString() + " выиграл в лотерею и разбогател");
        }
        else{
            Main.pause("Персонаж " + this.toString() + " проиграл в лотерею.");
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        int prime = 5;
        int result = 1;
        result = prime * result + super.hashCode() + hairColor.toString().length() +
                bodyType.toString().length() + currentTalkID + wallet.hashCode();
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
        Human other = (Human) obj;

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

        if (hairColor != other.hairColor)
            return false;
        if (bodyType != other.bodyType)
            return false;
        if (!wallet.equals(other.wallet))
            return false;
        return true;
    }
}
