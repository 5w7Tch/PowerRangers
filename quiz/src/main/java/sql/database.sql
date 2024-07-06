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

insert into users values (1,'nika','nika@', '34bff7be484da58a7c244a79ef278630f334a732',  true);

insert into users values (2,'lasha','lasha@', 'ee5d0f40184e345d01bf17e5a8a8dab7bcf0c4c8',  true);

<<<<<<< HEAD
insert into quizzes values (1,1,'ito arabets rostevan', sysdate(), 'it was created to ftest something', true, true,false, 30);

insert into quizzes values (2,2,'lashqar mravali kmiani', sysdate(), 'it was created to ftest somethingoooooooooooooooooooooooooooooooooo ooooooooooooooooooooooooooo ooooooooooooooooooooooooooooooooooooooooo oooooooooooooooooooooooooooooooooooooooooooooooooooooo ooooooooooooooooooooooooooooooooo', true, false,false, 30);
=======
insert into quizzes values (1,1,'ito arabets rostevan', sysdate(), 'it was created to ftest something', true, true, false, 30);

insert into quizzes values (2,2,'lashqar mravali kmiani', sysdate(), 'it was created to ftest something', true, true, false, 30);
>>>>>>> origin/questionClasses

insert into quizHistory values (1,1,1,DATE_SUB(NOW(), INTERVAL 10 MINUTE) ,sysdate() , 30);

insert into quizHistory values (2,1,1,DATE_SUB(NOW(), INTERVAL 10 MINUTE) ,sysdate() , 40);

insert into quizHistory values (3,1,1,DATE_SUB(NOW(), INTERVAL 30 MINUTE) ,sysdate() , 78);

INSERT INTO notes (fromId, toId, text, sendTime)
SELECT
    u1.userId AS fromId,
    u2.userId AS toId,
    CONCAT('Random text ', FLOOR(1 + RAND() * 1000)) AS text,
    NOW() - INTERVAL FLOOR(RAND() * 10) DAY AS sendTime
FROM users u1
         JOIN users u2 ON u1.userId != u2.userId
ORDER BY RAND()
LIMIT 10;

INSERT INTO challenges (fromId, toId, quizId, sendTime)
SELECT
    u1.userId AS fromId,
    u2.userId AS toId,
    q.quizId AS quizId,
    NOW() - INTERVAL FLOOR(RAND() * 10) DAY AS sendTime
FROM users u1
         JOIN users u2 ON u1.userId != u2.userId
         JOIN quizzes q ON q.quizId = (SELECT quizId FROM quizzes ORDER BY RAND() LIMIT 1)
ORDER BY RAND()
LIMIT 10;

DROP PROCEDURE IF EXISTS insert_random_friend_requests;

DELIMITER $$

CREATE PROCEDURE insert_random_friend_requests(IN num_requests INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE max_user_id INT;

    SELECT MAX(userId) INTO max_user_id FROM users;

    WHILE i < num_requests DO
            INSERT INTO friendRequests (fromUserId, toUserId, sendTime)
            VALUES (
                       FLOOR(1 + RAND() * max_user_id),
                       FLOOR(1 + RAND() * max_user_id),
                       NOW() - INTERVAL FLOOR(RAND() * 365) DAY
                   );
            SET i = i + 1;
        END WHILE;
END $$

DELIMITER ;

CALL insert_random_friend_requests(3);

INSERT INTO achievements (icon, type, description) VALUES
                                                             ('/static/icons/achievements/AmateurAuthor.png', 0, 'You have created a quiz.'),
                                                             ('/static/icons/achievements/ProlificAuthor.png', 1, 'You have created five quizzes.'),
                                                             ('/static/icons/achievements/ProdigiousAuthor.png', 2, 'You have created ten quizzes.'),
                                                             ('/static/icons/achievements/QuizMachine.png', 3, 'You have took ten quizzes.'),
                                                             ('/static/icons/achievements/IamtheGreatest.png', 4, 'You have had the highest score on a quiz.'),
                                                             ('/static/icons/achievements/PracticeMakesPerfect.png', 5, 'You have took a quiz in practice mode.');

INSERT INTO userAchievements (userId, achievementId, timeStamp) VALUES
                                                                    (2, 1, '2024-07-01'),
                                                                    (2, 2, '2024-07-02'),
                                                                    (2, 3, '2024-07-03'),
                                                                    (2, 4, '2024-07-01'),
                                                                    (2, 5, '2024-07-04'),
                                                                    (2, 6, '2024-07-05');