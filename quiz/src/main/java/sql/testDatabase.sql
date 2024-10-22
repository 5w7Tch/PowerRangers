drop database if exists quiztestdb;
create database quiztestdb;

USE quiztestdb;

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
      fromUserId int not null,
      toUserId int not null,
      sendTime DATETIME NOT NULL,
      foreign key (fromUserId) references users(userId),
      foreign key (toUserId) references users(userId)
);

create table if not exists friends(
      friendId int primary key auto_increment,
      user1Id int not null,
      user2Id int not null,
      timeStamp DATE NOT NULL,
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
      immediateCorrection BOOLEAN not null,
      quizTime DOUBLE,
      FOREIGN KEY (author) REFERENCES users(userId)
);

CREATE TABLE IF NOT EXISTS quizHistory (
      historyId INT PRIMARY KEY AUTO_INCREMENT,
      quizId INT NOT NULL ,
      userId INT NOT NULL ,
      startTime DATETIME NOT NULL ,
      spentTime DOUBLE NOT NULL ,
      score Double NOT NULL ,
      FOREIGN KEY (quizId) REFERENCES quizzes(quizId),
      FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE if not exists notes (
     noteId INT AUTO_INCREMENT PRIMARY KEY,
     fromId INT NOT NULL,
     toId INT NOT NULL,
     text TEXT NOT NULL,
     sendTime DATETIME NOT NULL,
     FOREIGN KEY (fromId) REFERENCES users(userId),
     FOREIGN KEY (toId) REFERENCES users(userId)
);

CREATE TABLE if not exists challenges (
    challengeId INT AUTO_INCREMENT PRIMARY KEY,
    fromId INT NOT NULL,
    toId INT NOT NULL,
    quizId INT NOT NULL,
    sendTime DATETIME NOT NULL,
    FOREIGN KEY (fromId) REFERENCES users(userId),
    FOREIGN KEY (toId) REFERENCES users(userId),
    FOREIGN KEY (quizId) REFERENCES quizzes(quizId)
);

CREATE TABLE if not exists questions(
    questionId INT AUTO_INCREMENT PRIMARY KEY ,
    quizId INT NOT NULL,
    type nvarchar(30) NOT NULL,
    questionJson TEXT NOT NULL,
    answerJson TEXT not null,
    orderNum INT NOT NULL,
    score DOUBLE NOT NULL,
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


INSERT INTO achievements (icon, type, description) VALUES
                                                       ('/static/icons/achievements/AmateurAuthor.png', 0, 'You have created a quiz.'),
                                                       ('/static/icons/achievements/ProlificAuthor.png', 1, 'You have created five quizzes.'),
                                                       ('/static/icons/achievements/ProdigiousAuthor.png', 2, 'You have created ten quizzes.'),
                                                       ('/static/icons/achievements/QuizMachine.png', 3, 'You have took ten quizzes.'),
                                                       ('/static/icons/achievements/IamtheGreatest.png', 4, 'You have had the highest score on a quiz.'),
                                                       ('/static/icons/achievements/PracticeMakesPerfect.png', 5, 'You have took a quiz in practice mode.');
