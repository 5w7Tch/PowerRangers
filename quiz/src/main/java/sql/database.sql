USE quizdb;

drop table if exists friendRequests;
drop table if exists friends;
drop table if exists quizHistory;
drop table if exists notes;
drop table if exists challenges;
drop table if exists quizzes;
drop table if exists users;

create table if not exists users(
    userId int primary key auto_increment,
    firstName nvarchar(50) unique not null,
    email nvarchar(50) unique not null,
    passwordHash nvarchar(100) not null,
    isAdmin bool not null default false
);

create table if not exists friendRequests(
      requestId int primary key auto_increment,
      fromUserId int not null ,
      toUserId int not null ,
      foreign key (fromUserId) references users(userId),
      foreign key (toUserId) references users(userId)
);

create table if not exists friends(
      friendId int primary key auto_increment,
      user1Id int not null,
      user2Id int not null,
      foreign key (user1Id) references users(userId),
      foreign key (user2Id) references users(userId)
);


CREATE TABLE IF NOT EXISTS quizzes (
      quizId INT PRIMARY KEY AUTO_INCREMENT,
      author INT NOT NULL,
      name NVARCHAR(255),
      creationDate DATE NOT NULL ,
      description TEXT,
      isPracticable BOOLEAN DEFAULT TRUE,
      quizTime INT,
      FOREIGN KEY (author) REFERENCES users(userId)
);


CREATE TABLE IF NOT EXISTS quizHistory (
      historyId INT PRIMARY KEY AUTO_INCREMENT,
      quizId INT NOT NULL ,
      userId INT NOT NULL ,
      startTime DATETIME NOT NULL ,
      endTime DATETIME NOT NULL ,
      score INT NOT NULL ,
      FOREIGN KEY (quizId) REFERENCES quizzes(quizId),
      FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE if not exists notes (
     noteId INT AUTO_INCREMENT PRIMARY KEY,
     fromId INT NOT NULL,
     toId INT NOT NULL,
     text TEXT NOT NULL,
     FOREIGN KEY (fromId) REFERENCES users(userId),
     FOREIGN KEY (toId) REFERENCES users(userId)
);

CREATE TABLE if not exists challenges (
    challengeId INT AUTO_INCREMENT PRIMARY KEY,
    fromId INT NOT NULL,
    toId INT NOT NULL,
    quizId INT NOT NULL,
    FOREIGN KEY (fromId) REFERENCES users(userId),
    FOREIGN KEY (toId) REFERENCES users(userId),
    FOREIGN KEY (quizId) REFERENCES quizzes(quizId)
);
