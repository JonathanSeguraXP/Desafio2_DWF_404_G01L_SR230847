
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     first_name VARCHAR(100) NOT NULL,
                                     last_name VARCHAR(100) NOT NULL,
                                     email VARCHAR(255) UNIQUE NOT NULL,
                                     created_at TIMESTAMP,
                                     updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS subscriptions (
                                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                             name VARCHAR(255) NOT NULL,
                                             start_date DATE NOT NULL,
                                             end_date DATE NOT NULL,
                                             is_active BOOLEAN,
                                             created_at TIMESTAMP,
                                             updated_at TIMESTAMP,
                                             user_id BIGINT NOT NULL,
                                             FOREIGN KEY (user_id) REFERENCES users(id)
);