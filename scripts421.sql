alter table student add constraint CHECK(age > 10);
alter table student add constraint name unique(name);
alter table faculty add constraint name_and_color unique(name,color);
alter table student alter column age set default 20;

