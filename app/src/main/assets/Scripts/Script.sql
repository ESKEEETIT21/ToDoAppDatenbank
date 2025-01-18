CREATE TABLE tasks(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    priority INTEGER NOT NULL,
    enddate DATETIME NOT NULL,
    description TEXT NOT NULL, 
    state BOOLEAN NOT NULL,
    FOREIGN KEY (priority) REFERENCES priorities(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);