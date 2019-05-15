package Mind;

import Buildings.Building;

import java.util.ArrayList;

public class Memory {
    private ArrayList<Thought> thoughts;

    public Memory(){
        thoughts = new ArrayList<>();
    }

    public Thought getRandomThought(){
        int first_border = 0;
        int end_border = thoughts.size()-1;
        int random = first_border + (int) (Math.random() *
                ((end_border - first_border) + 1));
        if(thoughts.size() == 0){
            return null;
        }
        return thoughts.get(random);
    }

    public void remember(Building building){
        thoughts.add(new Thought(building));
        if (thoughts.size() > 15){
            thoughts.remove(0);
        }
    }

    public void forget(Building building){
        thoughts.remove(new Thought(building));
    }

    @Override
    public String toString() {
        return "Память с числом мыслей равным " + thoughts.size();
    }

    @Override
    public int hashCode() {
        int prime = 15;
        int result = 1;
        result = prime * result + thoughts.size();
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
        Memory other = (Memory) obj;
        if (!thoughts.equals(other.thoughts))
            return false;
        return true;
    }
}
