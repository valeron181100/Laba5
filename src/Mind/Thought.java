package Mind;

import Buildings.Building;
import Entities.LivingEntity;
import Enums.SentenceType;

public class Thought {
    private Building object;

    public Thought(Building buildingObj){
        this.object = buildingObj;
    }

    public Building getObject() {
        return object;
    }

    public String generateSentence(SentenceType type, String lastSentence){
        String sentence = "";
        String[] posSentences = {"Его высота составляет % метров.", "Это %"};
        String[] negSentences = {"Мне кажется, что это % не очень красивое здание.", "Мне не нравится эта %."};
        String[] questSentences = {"Что это за здание?", "Какая высота у этого здания, которое %?"};

        int random = (int) (Math.random() * 2);
        switch (type){
            case Negative:
                return negSentences[random].replace("%", object.getType().getRussianTranslation());
            case Question:
                return questSentences[random].replace("%", object.getType().getRussianTranslation());
            case Positive:
                if(lastSentence != null && lastSentence.length() != 0){
                    if (lastSentence.contains("высота")){
                        return posSentences[0].replace("%", String.valueOf(object.getHeightMeters()));
                    }
                    else {
                        return posSentences[1].replace("%", object.getType().getRussianTranslation());
                    }
                }
                break;
        }

        return null;
    }

    @Override
    public String toString() {
        return "Мысль о здании " + object.toString();
    }

    @Override
    public int hashCode() {
        int prime = 16;
        int result = 1;
        result = prime * result + object.hashCode();
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
        Thought other = (Thought) obj;
        if (!object.equals(other.object))
            return false;
        return true;
    }
}
