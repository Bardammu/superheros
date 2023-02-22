TRUNCATE TABLE superhero RESTART IDENTITY;
INSERT INTO superhero (name, gender, origin, birthdate) VALUES ('Superman', 'Male', 'Krypton', PARSEDATETIME('1983-02-29','yyyy-MM-dd'));
INSERT INTO superhero (name, gender, origin, birthdate) VALUES ('Iron Man', 'Male', 'New York, US, Earth', PARSEDATETIME('1970-05-29','yyyy-MM-dd'));
