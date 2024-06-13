USE quizdb;



CREATE TABLE if not exists notes (
    noteId BIGINT AUTO_INCREMENT PRIMARY KEY,
    fromId BIGINT NOT NULL,
    toId BIGINT NOT NULL,
    text TEXT NOT NULL,
    FOREIGN KEY (fromId) REFERENCES users(userId),
    FOREIGN KEY (toId) REFERENCES users(userId)
);

CREATE TABLE if not exists challenges (
    challengeId BIGINT AUTO_INCREMENT PRIMARY KEY,
    fromId BIGINT NOT NULL,
    toId BIGINT NOT NULL,
    quizId BIGINT NOT NULL,
    FOREIGN KEY (fromId) REFERENCES users(userId),
    FOREIGN KEY (toId) REFERENCES users(userId),
    FOREIGN KEY (quizId) REFERENCES quizzes(quizId)
);
