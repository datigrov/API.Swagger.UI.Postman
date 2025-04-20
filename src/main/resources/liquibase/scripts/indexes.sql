-- liquibase formatted sql

-- changeset denis:indexes
   create index student_index_names on student(name);
   create index faculty_intexes on faculty(name,color);
