package Talks;

public interface PaperMessage {
    String getMessage();
    String getSender();
    String getReceiver();
    void create(String sender, String receiver, String msg);
}
