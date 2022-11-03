package object.enums;

public enum HouseTypeEnum {
    UNKNOWN,BUNGALOWS, BUILDINGS_WITH_BALCONIES, DETACHED_HOUSES;

    public static HouseTypeEnum judgeHouseTypeFromString(String s){
        switch (s){
            case "bungalows":
                return BUNGALOWS;
            case "buildings with balconies":
                return BUILDINGS_WITH_BALCONIES;
            case "detached houses":
                return DETACHED_HOUSES;
            default:
                return UNKNOWN;
        }
    }
}
