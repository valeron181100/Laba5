package Buildings;

import Entities.LivingEntity;
import Enums.BuildingType;

import java.util.ArrayList;
import java.util.Arrays;

public class Building {
    private ArrayList<LivingEntity> members;
    private BuildingType kind;
    private int heightMeters;

    public Building(BuildingType type, int heightMeters, LivingEntity ... entities){
        kind = type;
        members = new ArrayList<>();
        members.addAll(Arrays.asList(entities));
        this.heightMeters = heightMeters;
    }

    public BuildingType getType() {
        return kind;
    }

    public ArrayList<LivingEntity> getMembers() {
        return members;
    }

    public int getHeightMeters() {
        return heightMeters;
    }

    @Override
    public String toString() {
        return kind.getRussianTranslation();
    }

    @Override
    public int hashCode() {
        int prime = 1;
        int result = 1;
        result = prime * result + members.size() + kind.toString().length() + heightMeters;
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
        Building other = (Building) obj;
        if (!members.equals(other.members))
            return false;
        if (kind != other.kind)
            return false;
        if ( heightMeters != other.heightMeters)
            return false;
        return true;
    }
}
