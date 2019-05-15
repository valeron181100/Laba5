package Enums;

public enum BuildingType {
    School("Школа"),
    Hospital("Больница"),
    University("Университет"),
    Shop("Магазин"),
    Dwelling("Жилой дом");

    private String russianTranslation;

    BuildingType(String russianTranslation){
        this.russianTranslation = russianTranslation;
    }

    public String getRussianTranslation() {
        return russianTranslation;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
