-- liquibase formatted sql

-- changeset enotik:student-name-index
CREATE INDEX IF NOT EXISTS idx_student_name ON student (name);

-- changeset enotik:faculty-name-index
CREATE INDEX IF NOT EXISTS idx_faculty_name_color
    ON faculty (name, color);


