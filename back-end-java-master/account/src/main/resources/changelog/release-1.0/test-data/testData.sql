insert into users (fathers_name, first_name, last_name, type) values
    ('adminich', 'admin', 'adminenko', 'TEACHER');
insert into users (fathers_name, first_name, last_name, type) values
    ('userich', 'user', 'userenko', 'TEACHER');

insert into account (active, email, password, username, user_id)
    select true, 'admin@example.com', '$2a$10$xByp2q7ImdSvb1w/sOuUK.2g.uSBwjZSg2RLy94SzE2iM.ezBROOW', 'admin', users.user_id
    from users where first_name = 'admin';
insert into account (active, email, password, username, user_id)
    select true, 'user@example.com', '$2a$10$xByp2q7ImdSvb1w/sOuUK.2g.uSBwjZSg2RLy94SzE2iM.ezBROOW', 'user', users.user_id
    from users where first_name = 'user';

insert into account_role (account_id, role_id)
    select account.account_id, role.role_id
    from role, account where role.name = 'ADMIN' and account.username = 'admin';
insert into account_role (account_id, role_id)
    select account.account_id, role.role_id
    from role, account where role.name = 'USER' and account.username = 'user';

insert into faculty (name) values
    ('FIOT'), ('FPM'), ('TEF');

-- TEF
insert into cathedra (name, faculty_id)
    select 'CS', faculty_id
    from faculty where name='TEF';

insert into cathedra (name, faculty_id)
    select 'Energy', faculty_id
    from faculty where name='TEF';

insert into cathedra (name, faculty_id)
    select 'Heat', faculty_id
    from faculty where name='TEF';

-- FPM
insert into cathedra (name, faculty_id)
    select 'SE', faculty_id
    from faculty where name='FPM';

insert into cathedra (name, faculty_id)
    select 'Applied Math', faculty_id
    from faculty where name='FPM';

insert into cathedra (name, faculty_id)
    select 'Linear Algebra', faculty_id
    from faculty where name='FPM';

-- FIOT
insert into cathedra (name, faculty_id)
    select 'Calculation', faculty_id
    from faculty where name='FIOT';

insert into cathedra (name, faculty_id)
    select 'Computer Engineering', faculty_id
    from faculty where name='FIOT';

insert into cathedra (name, faculty_id)
    select 'Machine Learning', faculty_id
    from faculty where name='FIOT';

-- admin
insert into user_cathedra (user_id, cathedra_id)
    select users.user_id, cathedra.cathedra_id
    from users, cathedra where users.first_name = 'admin' and cathedra.name = 'Computer Engineering';
insert into user_cathedra (user_id, cathedra_id)
    select users.user_id, cathedra.cathedra_id
    from users, cathedra where users.first_name = 'admin' and cathedra.name = 'Machine Learning';
insert into user_cathedra (user_id, cathedra_id)
    select users.user_id, cathedra.cathedra_id
    from users, cathedra where users.first_name = 'admin' and cathedra.name = 'Applied Math';

-- user
insert into user_cathedra (user_id, cathedra_id)
    select users.user_id, cathedra.cathedra_id
    from users, cathedra where users.first_name = 'user' and cathedra.name = 'Calculation';
insert into user_cathedra (user_id, cathedra_id)
    select users.user_id, cathedra.cathedra_id
    from users, cathedra where users.first_name = 'user' and cathedra.name = 'Heat';
insert into user_cathedra (user_id, cathedra_id)
    select users.user_id, cathedra.cathedra_id
    from users, cathedra where users.first_name = 'user' and cathedra.name = 'SE';