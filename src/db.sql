create table conferences
(
    conference_id   int auto_increment
        primary key,
    conference_name varchar(255) null
);

create table assignments
(
    assignment_id          int auto_increment
        primary key,
    assignment_name        varchar(255)                     null,
    conference_id          int                              null,
    due_date               date default current_timestamp() not null,
    assignment_description varchar(255)                     null,
    constraint fk_conference_id
        foreign key (conference_id) references conferences (conference_id)
);

create table students
(
    student_id    int auto_increment
        primary key,
    student_name  varchar(255) null,
    student_email varchar(255) not null
);

create table student_assignments
(
    submission_id  int auto_increment
        primary key,
    student_id     int         not null,
    assignment_id  int         not null,
    date_submitted datetime(6) null,
    turnitin_score int         null,
    word_count     int         null,
    constraint student_assignments_ibfk_1
        foreign key (student_id) references students (student_id),
    constraint student_assignments_ibfk_2
        foreign key (assignment_id) references assignments (assignment_id),
    constraint student_assignments_ibfk_3
        foreign key (student_id) references students (student_id),
    constraint student_assignments_ibfk_4
        foreign key (assignment_id) references assignments (assignment_id)
);

create index AssignmentId
    on student_assignments (assignment_id);

create index student_id
    on student_assignments (student_id);

create table student_conferences
(
    participation_id int auto_increment
        primary key,
    student_id       int          not null,
    conference_id    int          not null,
    delegation       varchar(255) null,
    constraint student_conferences_ibfk_1
        foreign key (student_id) references students (student_id),
    constraint student_conferences_ibfk_2
        foreign key (conference_id) references conferences (conference_id),
    constraint student_conferences_ibfk_3
        foreign key (student_id) references students (student_id),
    constraint student_conferences_ibfk_4
        foreign key (conference_id) references conferences (conference_id)
);

create index conference_id
    on student_conferences (conference_id);

create index student_id
    on student_conferences (student_id);

