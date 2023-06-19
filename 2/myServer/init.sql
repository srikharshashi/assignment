CREATE DATABASE task2;

use task2;


CREATE TABLE student_info (
  name VARCHAR(50),
  rollnum INT,
  sub1_marks INT,
  sub2_marks INT,
  sub3_marks INT,
  sub4_marks INT
);

INSERT INTO student_info (name, rollnum, sub1_marks, sub2_marks, sub3_marks, sub4_marks)
VALUES
  ('John Doe', 1, 90, 85, 92, 88),
  ('Jane Smith', 2, 87, 95, 88, 90),
  ('Michael Johnson', 3, 92, 89, 94, 85),
  ('Emily Davis', 4, 85, 90, 88, 92),
  ('David Brown', 5, 88, 92, 87, 90);

select * from student_info;