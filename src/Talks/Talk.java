package Talks;

import Buildings.Building;
import Buildings.Street;
import Entities.Human;
import Entities.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Talk implements Talkative {
    private ArrayList<LivingEntity> members;
    private Question current_question;
    private Joke current_joke;
    private int talk_id;
    private Street street;

    public Talk(Street street, Building building, LivingEntity ... people){
        members = new ArrayList<>();
        for (LivingEntity p : people) {
            if (p.getPosition().isOutdoor() && p.getPosition().getStreet().equals(street) ||
                    p.getPosition().getBuilding().equals(people[0].getPosition().getBuilding())) {
                members.add(p);
            }
        }
        if (this.members.size() > 0) {
            talk_id = street.getTalks().size();
            this.street = street;
            street.addTalk(this);
            members.forEach(p -> p.setCurrentTalk(this));
        }
    }

    @Override
    public void set_current_quest(Question current_question) {
        this.current_question = current_question;
    }

    @Override
    public void set_current_joke(Joke current_joke) {
        this.current_joke = current_joke;
    }

    @Override
    public Question getCurrent_question() {
        return current_question;
    }

    @Override
    public Joke getCurrent_joke() {
        return current_joke;
    }

    public void add_members(Human... people){
        for (LivingEntity p : people) {
            if (p.getPosition().isOutdoor() && p.getPosition().getStreet().equals(street) ||
                    p.getPosition().getBuilding().equals(members.get(0).getPosition().getBuilding())) {
                members.add(p);
            }
        }
    }

    public void remove_members(Human ... people){
        members.removeAll(Arrays.asList(people));
    }

    @Override
    public void end(){
        street.removeTalk(talk_id);
    }

    public int getTalk_id() {
        return talk_id;
    }

    @Override
    public int hashCode() {
        int index = this.talk_id;
        return index;
    }

    @Override
    public String toString() {
        return "Talk no. " + this.talk_id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Talk other = (Talk) obj;
        if(!members.equals(other.members))
            return false;
        if(!current_question.equals(other.current_question))
            return false;
        if(!current_joke.equals(other.current_joke))
            return false;
        if(talk_id != other.talk_id)
            return false;
        if(!street.equals(other.street))
            return false;
        return true;
    }
}
