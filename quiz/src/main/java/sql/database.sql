<<<<<<< HEAD
use quizdb;

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
=======
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
>>>>>>> badriSQL
