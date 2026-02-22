CREATE TABLE tasks (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_id BIGINT NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       description VARCHAR(1000),
                       status VARCHAR(50) DEFAULT 'NEW',
                       completed_task_time TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);