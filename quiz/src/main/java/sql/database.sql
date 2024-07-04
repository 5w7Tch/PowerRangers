USE quizdb;

drop table if exists friendRequests;
drop table if exists friends;
drop table if exists quizHistory;
drop table if exists notes;
drop table if exists challenges;
drop table if exists questions;
drop table if exists quizzes;
drop table if exists announcements;
drop table if exists userAchievements;
drop table if exists achievements;
drop table if exists users;

create table if not exists users(
    userId int primary key auto_increment,
    firstName nvarchar(50) unique not null,
    email nvarchar(50) not null,
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
      areQuestionsRandom BOOLEAN default True,
      quizTime DOUBLE,
      immediateCorrection boolean,
      FOREIGN KEY (author) REFERENCES users(userId)
);


CREATE TABLE IF NOT EXISTS quizHistory (
      historyId INT PRIMARY KEY AUTO_INCREMENT,
      quizId INT NOT NULL ,
      userId INT NOT NULL ,
      startTime DATETIME NOT NULL ,
      endTime DATETIME NOT NULL ,
      score Double NOT NULL ,
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

CREATE TABLE if not exists questions(
    questionId INT AUTO_INCREMENT PRIMARY KEY ,
    quizId INT NOT NULL,
    type INT NOT NULL,
    text TEXT NOT NULL,
    orderNum INT NOT NULL,
    score INT NOT NULL,
    FOREIGN KEY (quizId) REFERENCES  quizzes(quizId)
);

CREATE TABLE if not exists announcements(
    announcementId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    text TEXT NOT NULL,
    timeStamp DATE NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE if not exists achievements(
    achievementId INT AUTO_INCREMENT PRIMARY KEY,
    name nvarchar(120) NOT NULL,
    icon TEXT NOT NULL,
    type INT NOT NUll,
    description TEXT NOT NULL
);

CREATE TABLE if not exists userAchievements(
    userAchievementId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    achievementId INT NOT NULL,
    timeStamp DATE NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(userId),
    FOREIGN KEY (achievementId) REFERENCES achievements(achievementId)
);

insert into users values (1,'nika','nika@', '34bff7be484da58a7c244a79ef278630f334a732',  true);

insert into quizzes values (1,1,'ito arabets rostevan', sysdate(), 'it was created to ftest something', true, true, 30, true);

insert into quizHistory values (1,1,1,DATE_SUB(NOW(), INTERVAL 10 MINUTE) ,sysdate() , 30);

insert into quizHistory values (2,1,1,DATE_SUB(NOW(), INTERVAL 10 MINUTE) ,sysdate() , 40);

insert into quizHistory values (3,1,1,DATE_SUB(NOW(), INTERVAL 30 MINUTE) ,sysdate() , 78);
