package models.enums;

public enum NotificationType
{
    NOTE("Note"),
    CHALLENGE("Challenge"),
    FRIEND_REQUEST("Friend Request");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
