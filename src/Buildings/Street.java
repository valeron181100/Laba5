package Buildings;

import Entities.LivingEntity;
import MainPkg.Main;
import Talks.Talk;

import java.util.ArrayList;
import java.util.Arrays;

public class Street {
    private String name;
    private ArrayList<LivingEntity> entities;
    private ArrayList<Building> streetBuildings;
    private ArrayList<Talk> talks;

    public Street(String name,Building ... buildings){
        entities = new ArrayList<>();
        streetBuildings = new ArrayList<>();
        talks = new ArrayList<>();
        this.name = name;

        streetBuildings.addAll(Arrays.asList(buildings));
    }

    public void addEntities(LivingEntity ... entities){
        this.entities.addAll(Arrays.asList(entities));
        this.entities.forEach(p->{
            p.getPosition().setStreet(this);
            Main.pause(String.format("Персонаж %s был добавлен на улицу %s", p.getName(), this.toString()));
        });
    }

    public void removeEntities(LivingEntity ... entities){
        this.entities.removeAll(Arrays.asList(entities));
    }

    public ArrayList<LivingEntity> getEntities() {
        return entities;
    }

    public ArrayList<Talk> getTalks() {
        return talks;
    }

    public ArrayList<Building> getStreetBuildings() {
        return streetBuildings;
    }

    public void addTalk(Talk talk){
        talks.add(talk.getTalk_id(), talk);
    }

    public Talk getTalk(int id){
        ArrayList<Talk> b = this.talks;
        return talks.get(id);
    }

    public void removeTalk(Talk talk){
        talks.remove(talk);
    }

    public void removeTalk(int id){
        talks.remove(id);
    }


    @Override
    public String toString() {
        return this.name;
    }
    @Override
    public int hashCode() {
        int prime = 3;
        int result = 1;
        result = prime * result + name.length() + entities.size()
                + streetBuildings.size() + talks.size();
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
        Street other = (Street) obj;
        if (!name.equals(other.name))
            return false;
        if (!entities.equals(other.entities))
            return false;
        if (!streetBuildings.equals(other.streetBuildings))
            return false;
        if (!talks.equals(other.talks))
            return false;
        return true;
    }
}
