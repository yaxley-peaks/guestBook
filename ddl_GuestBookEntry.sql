CREATE TABLE guest_book_entry
(
    id           SERIAL NOT NULL,
    submitter    TEXT,
    message      TEXT,
    submitted_on TIMESTAMP WITHOUT TIME ZONE,
    ip           VARCHAR(255),
    CONSTRAINT pk_guestbookentry PRIMARY KEY (id)
);
CREATE SEQUENCE guest_book_entry_seq;