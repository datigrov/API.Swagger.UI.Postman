create table car(
    car_id integer primary key,
    model varchar,
    brand varchar,
    price integer
);

create table human(
    human_id integer primary key,
    name varchar,
    age_human integer,
    human_license boolean
);
create table human_and_car(
    human_id Integer references car(car_id)
);