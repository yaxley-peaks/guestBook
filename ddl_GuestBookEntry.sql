CREATE TABLE guest_book_entry
(
    id           SERIAL NOT NULL,
    submitter    TEXT,
    message      TEXT,
    submitted_on TIMESTAMP WITHOUT TIME ZONE,
    ip           VARCHAR(255),
    CONSTRAINT pk_guestbookentry PRIMARY KEY (id)
);
CREATE TABLE guest_book_entry
(
    id           BIGINT NOT NULL,
    submitter    VARCHAR(255),
    message      VARCHAR(255),
    submitted_on TIMESTAMP WITHOUT TIME ZONE,
    ip           VARCHAR(255),
    CONSTRAINT pk_guestbookentry PRIMARY KEY (id)
);