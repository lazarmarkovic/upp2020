
insert into camunda.aa__genres(id, code, name) values (1, 'FILOZOFIJA', 'Filozofija');
insert into camunda.aa__genres(id, code, name) values (2, 'UMETNOST', 'Umetnost');
insert into camunda.aa__genres(id, code, name) values (3, 'NAUKA', 'Nauka');

/* Readers */
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (1, 'pera', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'READER',
        'lazar.kmarkovic@gmail.com', 'Pera', 'Peric', 'Novi Sad', 'Srbija', '1234567', true, true);


/* Committee members */
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (2, 'comm1', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'COMMITTEE_MEMBER',
        'lazar.kmarkovic+1@gmail.com', 'comm', 'jedan', 'Novi Sad', 'Srbija', '1234567', true, true);
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (3, 'comm2', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'COMMITTEE_MEMBER',
        'lazar.kmarkovic+2@gmail.com', 'comm', 'dva', 'Novi Sad', 'Srbija', '1234567', true, true);
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (4, 'comm3', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'COMMITTEE_MEMBER',
        'lazar.kmarkovic+3@gmail.com', 'comm', 'tri', 'Novi Sad', 'Srbija', '1234567', true, true);
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (5, 'comprez', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'COMMITTEE_PRESIDENT',
        'lazar.kmarkovic+4@gmail.com', 'comprez', 'comprez', 'Novi Sad', 'Srbija', '1234567', true, true);


/* Writers */
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (6, 'writer1', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'AUTHOR',
        'lazar.kmarkovic+5@gmail.com', 'Writer1', 'Writer1', 'Novi Sad', 'Srbija', '1234567', true, true);


/* Head Editor */
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (7, 'head_editor', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'HEAD_EDITOR',
        'lazar.kmarkovic+6@gmail.com', 'HeadEditor', 'HeadEditor', 'Novi Sad', 'Srbija', '1234567', true, true);

/* Beta readers */
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (8, 'beta1', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'BETA_READER',
        'lazar.kmarkovic+7@gmail.com', 'Beta1', 'Beta1', 'Valjevo', 'Srbija', '1234567', true, true);
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (9, 'beta2', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'BETA_READER',
        'lazar.kmarkovic+8@gmail.com', 'Beta2', 'Beta2', 'Valjevo', 'Srbija', '1234567', true, true);
insert into camunda.aa__users(id, username, password, role, email,
                              first_name, last_name, city, country, activation_code, activated, approved)
values (10, 'beta3', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC', 'BETA_READER',
        'lazar.kmarkovic+8@gmail.com', 'Beta2', 'Beta2', 'Valjevo', 'Srbija', '1234567', true, true);

/* Beta reader - Genre */
insert into camunda.aa__user_genres(id, genre_id, user_id) value (1, 3, 8);
insert into camunda.aa__user_genres(id, genre_id, user_id) value (2, 2, 9);
insert into camunda.aa__user_genres(id, genre_id, user_id) value (3, 3, 10);