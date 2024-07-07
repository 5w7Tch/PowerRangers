package models.enums;

public enum ActivityType
{
    NOTE("Note"),
    CHALLENGE("Challenge"),
    FRIEND_REQUEST("Friend Request"),
    ANNOUNCEMENT("Announcement"),
    ACHIEVEMENT("Achievement"),
    FRIENDSHIP("Friendship"),
    WROTE_QUIZ("Wrote Quiz"),
    CREATED_QUIZ("Created Quiz");

    private final String displayName;

    ActivityType(String displayName) {
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
