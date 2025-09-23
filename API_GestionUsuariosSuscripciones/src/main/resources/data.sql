--EXAMPLE
INSERT INTO users (id, first_name, last_name, email, created_at, updated_at)

VALUES
    (1, 'Jonathan', 'Tester', 'jonathan@udb.edu.sv', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Ana', 'Lopez', 'ana.lopez@udb.edu.sv', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ===============================
-- EXAMPLE
-- ===============================
INSERT INTO subscriptions (id, name, start_date, end_date, is_active, created_at, updated_at, user_id)
VALUES
    (1, 'Netflix', CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    (2, 'Spotify', CURRENT_DATE, DATEADD('DAY', 60, CURRENT_DATE), TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
    (3, 'Disney+', CURRENT_DATE, DATEADD('DAY', 90, CURRENT_DATE), FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);