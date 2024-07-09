package models.enums;

public enum AchievementType
{
    AMATEUR_AUTHOR("Amateur Author"),
    PROLIFIC_AUTHOR("Prolific Author"),
    PRODIGIOUS_AUTHOR("Prodigious Author"),
    QUIZ_MACHINE("Quiz Machine"),
    I_AM_THE_GREATEST("I am the Greatest"),
    PRACTICE_MAKES_PERFECT("Practice Makes Perfect");

    private final String displayName;

    AchievementType(String displayName) {
        this.displayName = displayName;
    }

    public static AchievementType fromOrdinal(int ordinal) {
        AchievementType[] types = AchievementType.values();
        if (ordinal < 0 || ordinal >= types.length) {
            throw new IllegalArgumentException("Invalid ordinal " + ordinal);
        }
        return types[ordinal];
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
