SELECT s.name AS student_name,
       s.age,
       f.name AS faculty_name,
       f.color AS faculty_color
FROM student s
         FULL JOIN faculty f
                   ON s.faculty_id = f.id;


ALTER TABLE student
    ADD COLUMN avatar_id INT;

ALTER TABLE student
    ADD CONSTRAINT student_avatar_fk
        FOREIGN KEY (avatar_id) REFERENCES avatar(id);

SELECT s.name AS student_name,
       s.age,
       a.file_path AS avatar_file
FROM student s
         INNER JOIN avatar a
                    ON s.avatar_id = a.id;