CREATE TABLE accounts (
            id  INTEGER PRIMARY KEY,
            login VARCHAR(255) NOT NULL,
            password VARCHAR(255) NOT NULL,
            role VARCHAR(255) NOT NULL
        );

CREATE TABLE meta_files (
            id  INTEGER PRIMARY KEY,
            host INTEGER NOT NULL, FOREIGN KEY(host) REFERENCES accounts(id),
            targetFileName VARCHAR(255) NOT NULL,
            submittedFileName VARCHAR(255) NOT NULL,
            directory VARCHAR(255) NOT NULL
        );
