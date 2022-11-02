package object.enums;

public enum GenderEnum {
    UNKNOWN,MALE,FEMALE,HELICOPTER;

    public static GenderEnum judgeGenderFromString(String s){
        switch (s){
            case "male":
                return MALE;
            case "female":
                return FEMALE;
            case "helicopter":
                return HELICOPTER;
            default:
                return UNKNOWN;
        }
    }
}
