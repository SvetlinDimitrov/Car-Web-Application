USE mobilele ;

CREATE TABLE IF NOT EXISTS `brandInit` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `created` date DEFAULT NULL,
    `modified` date DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `brand` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `created` date DEFAULT NULL,
    `modified` date DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL
);

insert into brandInit (created, modified, name)
VALUES ('1916-03-07' , '1924-07-11' , 'BMWW' ) ,
       ('1926-02-18', '1956-02-13' , 'Mercedes-Benz') ,
       ('1909-06-16' , null , 'Audi');

INSERT INTO `brand` (created, modified, name)
SELECT created , modified , name
from brandInit
WHERE (SELECT count(*) from brand) = 0;

DROP table brandInit ;

CREATE TABLE IF NOT EXISTS `model` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `category` ENUM ('Car', 'Buss', 'Truck', 'Motorcycle') DEFAULT NULL,
    `created` int DEFAULT NULL,
    `generation` int DEFAULT NULL,
    `image_url` varchar(512) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL UNIQUE ,
    `brand_id` bigint DEFAULT NULL,
CONSTRAINT FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
);

CREATE TABLE IF NOT EXISTS `modelInit` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `category` ENUM ('Car', 'Buss', 'Truck', 'Motorcycle') DEFAULT NULL,
    `created` int DEFAULT NULL,
    `generation` int DEFAULT NULL,
    `image_url` varchar(512) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL UNIQUE ,
    `brand_id` bigint DEFAULT NULL,
    CONSTRAINT FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
);

insert into modelInit (CATEGORY, CREATED, GENERATION, IMAGE_URL, NAME, BRAND_ID)
values
    ('Car' , 2008 , 5 , 'https://s1.cdn.autoevolution.com/images/models/BMW_X6-2023_main.jpg', 'BMW X6' , 1),
    ('Car' , 2014 , 2 , 'https://s1.cdn.autoevolution.com/images/models/BMW_M4-Competition-Convertible-M-xDrive-2021_main.jpg', 'BMW M4 Convertible' , 1),
    ('Car' , 2021 , 1 , 'https://s1.cdn.autoevolution.com/images/models/BMW_iX-2021_main.jpg' , 'BMW iX', 1),
    ('Car' , 2019 , 1 , 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/2019_Mercedes-Benz_A220%2C_front_8.22.19.jpg/1920px-2019_Mercedes-Benz_A220%2C_front_8.22.19.jpg' , 'A 180 Progressive', 2),
    ('Car' , 2021 , 1 , 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Mercedes-Benz_W213_Facelift_IMG_5257.jpg/1920px-Mercedes-Benz_W213_Facelift_IMG_5257.jpg','W213 Facelift', 2),
    ('Car' , 2021 , 1 , 'https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Mercedes-Benz_X243_IAA_2021_1X7A0093.jpg/1920px-Mercedes-Benz_X243_IAA_2021_1X7A0093.jpg', 'X243', 2),
    ('Car' , 2021 , 1 , 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Audi_e-tron_GT_IMG_5689.jpg/1920px-Audi_e-tron_GT_IMG_5689.jpg', 'e-tron_GT', 3),
    ('Car' , 2021 , 1 , 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Audi_Q4_e-tron_50_Quattro_IMG_5532.jpg/1920px-Audi_Q4_e-tron_50_Quattro_IMG_5532.jpg', 'e-tron_50_Quatrain', 3),
    ('Car' , 2010 , 1 , 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Audi_A1_Sportback_GB_IMG_6036.jpg/1920px-Audi_A1_Sportback_GB_IMG_6036.jpg', 'A1_Sport-back_GB', 3)
;

INSERT INTO `model` (category, created, generation, image_url, name, brand_id)
SELECT category, created, generation, image_url, name, brand_id
from modelInit
WHERE (SELECT count(*) from model) = 0;

drop table modelInit;

CREATE TABLE IF NOT EXISTS `userInit` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `created` date DEFAULT NULL,
    `first_name` varchar(255) DEFAULT NULL,
    `image_url` varchar(255) DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT NULL,
    `last_name` varchar(255) DEFAULT NULL,
    `modified` date DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `created` date DEFAULT NULL,
    `first_name` varchar(255) DEFAULT NULL,
    `image_url` varchar(255) DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT NULL,
    `last_name` varchar(255) DEFAULT NULL,
    `modified` date DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL UNIQUE
);

INSERT INTO userInit (created, first_name, image_url, is_active, last_name, modified, password, username)
VALUES
    ('2005-05-05' , 'Zaharia' , 'https://yt3.googleusercontent.com/ytc/AOPolaROJsray4rabTdqph4zpBJAt_01EwS5FbVlNfus=s900-c-k-c0x00ffffff-no-rj' , TRUE , 'Stepanov' , null , '12345' ,'Zaharia'),
    ('2015-05-05' , 'Ivan' , 'https://upload.wikimedia.org/wikipedia/commons/2/27/Ivan_Abadjiev.jpg' , TRUE , 'Ivanov' , null , '12345' ,'Ivan')
;

INSERT INTO user (id, created, first_name, image_url, is_active, last_name, modified, password, username)
SELECT *
FROM userInit
WHERE (SELECT COUNT(*) FROM user) = 0;

DROP TABLE userInit;

CREATE TABLE IF NOT EXISTS `offerInit` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created` date DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `engine` ENUM ('GASOLINE', 'DIESEL', 'ELECTRIC', 'HYBRID') DEFAULT NULL,
    `image_url` varchar(255) DEFAULT NULL,
    `mileage` int DEFAULT NULL,
    `modified` date DEFAULT NULL,
    `price` decimal(38,2) DEFAULT NULL,
    `transmission` ENUM ('MANUAL', 'AUTOMATIC') DEFAULT NULL,
    `year` int DEFAULT NULL,
    `model_id` bigint DEFAULT NULL,
    `seller_id` bigint DEFAULT NULL,
    CONSTRAINT FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`),
    CONSTRAINT FOREIGN KEY (`model_id`) REFERENCES `model` (`id`)
);

CREATE TABLE IF NOT EXISTS `offer` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created` date DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `engine` ENUM ('GASOLINE', 'DIESEL', 'ELECTRIC', 'HYBRID') DEFAULT NULL,
    `image_url` varchar(255) DEFAULT NULL,
    `mileage` int DEFAULT NULL,
    `modified` date DEFAULT NULL,
    `price` decimal(38,2) DEFAULT NULL,
    `transmission` ENUM ('MANUAL', 'AUTOMATIC') DEFAULT NULL,
    `year` int DEFAULT NULL,
    `model_id` bigint DEFAULT NULL,
    `seller_id` bigint DEFAULT NULL,
    CONSTRAINT FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`),
    CONSTRAINT FOREIGN KEY (`model_id`) REFERENCES `model` (`id`)
);

INSERT INTO offerInit (created, description, engine, image_url, mileage, modified, price, transmission, year, model_id, seller_id)
VALUES
    ('2020-02-02' , 'This car is perfect' , 'DIESEL' , null ,0 , null , '109890.230' , 'AUTOMATIC' , 2021 , 1 ,1),
    ('2020-02-02' , 'This car is perfect' , 'DIESEL' , null ,0 , null , '1020000' , 'AUTOMATIC' , 2021 , 2 ,1),
    ('2020-02-02' , 'This car is perfect' , 'DIESEL' , null ,0 , null , '10000' , 'AUTOMATIC' , 2021 , 3 ,1),
    ('2020-02-02' , 'This car is perfect' , 'DIESEL' , null ,0 , null , '1340000' , 'AUTOMATIC' , 2021 , 4 ,2),
    ('2020-02-02' , 'This car is perfect' , 'DIESEL' , null ,0 , null , '220000' , 'AUTOMATIC' , 2021 , 5 ,1),
    ('2020-02-02' , 'This car is perfect' , 'DIESEL' , null ,0 , null , '1323000' , 'AUTOMATIC' , 2021 , 5 ,2),
    ('2020-02-02' , 'This car is perfect' , 'DIESEL' , null ,0 , null , '1023230' , 'AUTOMATIC' , 2021 , 6 ,2)
;

INSERT INTO offer (ID, CREATED, DESCRIPTION, ENGINE, IMAGE_URL, MILEAGE, MODIFIED, PRICE, TRANSMISSION, YEAR, MODEL_ID, SELLER_ID)
SELECT * FROM offerInit
WHERE (SELECT COUNT(*) FROM offer) = 0;

DROP TABLE offerInit;

CREATE TABLE IF NOT EXISTS `user_role` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` ENUM ('USER' , 'ADMIN') DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `user_role_init` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` ENUM ('USER' , 'ADMIN') DEFAULT NULL
);

INSERT INTO user_role_init (ROLE)
VALUES
    ('USER'),('ADMIN')
;
INSERT INTO user_role (ID, ROLE)
SELECT * FROM user_role_init
WHERE (SELECT COUNT(*) FROM user_role) = 0;

DROP TABLE user_role_init;

CREATE TABLE IF NOT EXISTS `user_models` (
    `user_id` bigint NOT NULL,
    `model_id` bigint NOT NULL,
    CONSTRAINT FOREIGN KEY (`model_id`) REFERENCES `model` (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `user_roles_users` (
    `user_id` bigint NOT NULL,
    `user_role_id` bigint NOT NULL,
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`)
);

CREATE TABLE IF NOT EXISTS `user_roles_users_init` (
    `user_id` bigint NOT NULL,
    `user_role_id` bigint NOT NULL,
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`)
);

INSERT INTO user_roles_users_init (user_id, user_role_id)
VALUES
    (1 , 1),
    (1 , 2),
    (2 , 1),
    (2 , 2)
;

INSERT INTO user_roles_users (user_id, user_role_id)
SELECT * from user_roles_users_init
WHERE (SELECT COUNT(*) FROM user_roles_users) = 0;

DROP TABLE user_roles_users_init;