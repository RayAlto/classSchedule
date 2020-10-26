package top.rayalto.classSchedule.dataTypes;

public class Room {
    public int id;
    public String code;
    public String roomName;

    public Room() {
    }

    public Room(int id, String code, String roomName) {
        this.id = id;
        this.code = code;
        this.roomName = roomName;
    }
}