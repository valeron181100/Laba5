package Buildings;

import Entities.LivingEntity;
import MainPkg.Main;

public class Position {
    private int streetPos;
    private Street street;
    private Building building;
    private boolean isOutDoor;
    private LivingEntity entity;

    public Position(LivingEntity entity){
        this.entity = entity;
        street = null;
    }

    public int getStreetPos() {
        return streetPos;
    }
    public Street getStreet() {
        return street;
    }
    public Building getBuilding() {
        if (isOutDoor)
            return null;
        return building;
    }
    public boolean isOutdoor() {
        return isOutDoor;
    }

    public void setStreet(Street street) {
        this.street = street;
        isOutDoor = true;
    }
    public void setStreetPos(int streetPos) {
        this.streetPos = streetPos;
    }
    public void setBuilding(Building building) {
        if(building != null) {
            this.building = building;
            isOutDoor = false;
            Main.pause(String.format("Персонаж %s вошёл в здание %s.", entity.getName(), building.getType().getRussianTranslation()));
        }
        else{
            this.building = null;
            isOutDoor = true;
            Main.pause(String.format("Персонаж %s вышел из здания.", entity.getName()));
        }
    }

    @Override
    public String toString() {
        if(isOutDoor){
            return String.format("Улица: %s, Здание: %s.", this.street.toString(), this.building.getType().getRussianTranslation());
        }
        return String.format("Улица: %s, не в здании.", this.street.toString());
    }
    @Override
    public int hashCode() {
        int prime = 2;
        int outdoor = isOutDoor ? 1 : 0;
        int result = 1;
        result = result * prime + streetPos + street.hashCode() +
                building.hashCode() + outdoor + entity.hashCode();
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
        Position other = (Position) obj;
        if (streetPos != other.streetPos)
            return false;
        if(street != null && other.street != null)
            if (!street.equals(other.street))
                return false;
        if(building != null && other.building != null)
            if (!building.equals(other.building))
                return false;
        if (isOutDoor != other.isOutDoor)
            return false;
        return true;
    }
}
