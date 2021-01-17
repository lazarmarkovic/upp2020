
insert into camunda.aa__genre(id, code, name) values (1, 'FILOZOFIJA', 'Filozofija');
insert into camunda.aa__genre(id, code, name) values (2, 'UMETNOST', 'Umetnost');
insert into camunda.aa__genre(id, code, name) values (3, 'NAUKA', 'Nauka');

insert into camunda.aa__users(id, username, password, role, email, first_name, last_name, city, country, activation_code, activated)
values (1, 'pera', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'READER', 'lazar.kmarkovic+1@gmail.com',
        'Pera', 'Peric', 'Novi Sad', 'Srbija', '1234567', true);