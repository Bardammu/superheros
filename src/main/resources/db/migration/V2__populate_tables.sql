INSERT INTO superhero (name, gender, origin, birthdate) VALUES ('Superman', 'Male', 'Krypton', PARSEDATETIME('1983-02-29','yyyy-MM-dd'));
INSERT INTO superhero (name, gender, origin, birthdate) VALUES ('Iron Man', 'Male', 'New York, US, Earth', PARSEDATETIME('1970-05-29','yyyy-MM-dd'));

INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$ypzlQWZtEtaMebQfuAW4hOyFo0XeBH/emrpAIUfwJWtCY39hofWYa', true);

INSERT INTO authorities (username, authority) VALUES ('user', 'USER');