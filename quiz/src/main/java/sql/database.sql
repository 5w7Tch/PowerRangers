USE quizdb;

create table if not exists users(
    userId bigint primary key auto_increment,
    firstName nvarchar(50) unique not null,
    email nvarchar(50) unique not null,
    passwordHash nvarchar(100) not null,
    isAdmin bool not null default false
);

create table if not exists friendRequests(
      requestId bigint primary key auto_increment,
      fromUserId bigint not null ,
      toUserId bigint not null ,
      foreign key (fromUserId) references users(userId),
      foreign key (toUserId) references users(userId)
);

create table if not exists friends(
      friendId bigint primary key auto_increment,
      user1Id bigint not null,
      user2Id bigint not null,
      foreign key (user1Id) references users(userId),
      foreign key (user2Id) references users(userId)
);


CREATE TABLE IF NOT EXISTS quizzes (
      quizId BIGINT PRIMARY KEY AUTO_INCREMENT,
      author BIGINT NOT NULL,
      name NVARCHAR(255),
      creationDate DATE NOT NULL ,
      description TEXT,
      isPracticable BOOLEAN DEFAULT TRUE,
      quizTime BIGINT,
      FOREIGN KEY (author) REFERENCES users(userId)
);


CREATE TABLE IF NOT EXISTS quizHistory (
      historyId BIGINT PRIMARY KEY AUTO_INCREMENT,
      quizId BIGINT NOT NULL ,
      userId BIGINT NOT NULL ,
      startTime DATETIME NOT NULL ,
      endTime DATETIME NOT NULL ,
      score BIGINT NOT NULL ,
      FOREIGN KEY (quizId) REFERENCES quizzes(quizId),
      FOREIGN KEY (userId) REFERENCES users(userId)
);

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
