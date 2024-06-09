create table car(
    id integer primary key,
    model varchar,
    brand varchar,
    price integer
);

create table human(
    id serial primary key,
    name text not null,
    age integer not null,
    driver_license boolean,
    car_id integer not null references car(id)
);
