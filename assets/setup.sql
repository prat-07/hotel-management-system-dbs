-- =========================
-- DATABASE SETUP
-- =========================
DROP DATABASE IF EXISTS hotel_management_system;
CREATE DATABASE hotel_management_system;

\c hotel_management_system;

-- =========================
-- EXTENSIONS
-- =========================
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =========================
-- TABLES
-- =========================

CREATE TABLE customer (
                          cid BIGINT PRIMARY KEY CHECK (cid > 0),
                          fname VARCHAR(50) NOT NULL,
                          lname VARCHAR(50) NOT NULL,
                          street_no VARCHAR(50) NOT NULL,
                          city VARCHAR(50) NOT NULL,
                          state VARCHAR(50) NOT NULL,
                          pincode NUMERIC(6,0) NOT NULL CHECK (pincode > 0),
                          phone_no NUMERIC(10,0) NOT NULL UNIQUE CHECK (phone_no > 0)
);

CREATE TABLE room (
                      room_no INT PRIMARY KEY CHECK (room_no > 0),
                      type VARCHAR(30) NOT NULL CHECK (type IN ('STANDARD','DOUBLE','DELUXE','SUITE')),
                      price NUMERIC(10,2) NOT NULL CHECK (price > 0)
);

CREATE TABLE booking (
                         bid SERIAL PRIMARY KEY,
                         cid BIGINT NOT NULL,
                         room_no INT NOT NULL,
                         check_in DATE NOT NULL,
                         check_out DATE NOT NULL,
                         status VARCHAR(20) NOT NULL DEFAULT 'BOOKED',

                         CONSTRAINT booking_check CHECK (check_out > check_in),
                         CONSTRAINT chk_status CHECK (status IN ('BOOKED','CHECKED_IN','CHECKED_OUT')),

                         UNIQUE (room_no),
                         UNIQUE (cid, room_no),

                         FOREIGN KEY (cid) REFERENCES customer(cid) ON DELETE CASCADE,
                         FOREIGN KEY (room_no) REFERENCES room(room_no) ON DELETE CASCADE
);

CREATE TABLE bill (
                      bid INT PRIMARY KEY,
                      room_no INT NOT NULL,
                      check_in DATE NOT NULL,
                      check_out DATE NOT NULL,
                      total_amount NUMERIC(10,2) NOT NULL,
                      cid BIGINT NOT NULL
);

CREATE TABLE login (
                       username VARCHAR(50) PRIMARY KEY,
                       password TEXT NOT NULL
);

-- =========================
-- FUNCTIONS
-- =========================

CREATE OR REPLACE FUNCTION create_bill_on_checkin()
RETURNS TRIGGER AS $$
DECLARE
total_days INTEGER;
    room_price NUMERIC(10,2);
BEGIN
    IF NEW.status = 'CHECKED_IN'
       AND OLD.status IS DISTINCT FROM 'CHECKED_IN'
       AND NOT EXISTS (SELECT 1 FROM bill WHERE bid = NEW.bid)
    THEN
        total_days := GREATEST(NEW.check_out - NEW.check_in, 1);

SELECT price INTO room_price
FROM room
WHERE room_no = NEW.room_no;

INSERT INTO bill (bid, room_no, check_in, check_out, total_amount, cid)
VALUES (
           NEW.bid,
           NEW.room_no,
           NEW.check_in,
           NEW.check_out,
           total_days * room_price,
           NEW.cid
       );
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION delete_booking_on_checkout()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'CHECKED_OUT'
       AND OLD.status IS DISTINCT FROM 'CHECKED_OUT'
    THEN
DELETE FROM booking WHERE bid = OLD.bid;
RETURN NULL;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION hash_password()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' OR NEW.password IS DISTINCT FROM OLD.password THEN
        NEW.password := crypt(NEW.password, gen_salt('bf'));
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- =========================
-- TRIGGERS
-- =========================

CREATE TRIGGER trg_create_bill
    AFTER UPDATE ON booking
    FOR EACH ROW
    EXECUTE FUNCTION create_bill_on_checkin();

CREATE TRIGGER trg_delete_booking_on_checkout
    AFTER UPDATE ON booking
    FOR EACH ROW
    EXECUTE FUNCTION delete_booking_on_checkout();

CREATE TRIGGER trg_hash_password
    BEFORE INSERT OR UPDATE ON login
                         FOR EACH ROW
                         EXECUTE FUNCTION hash_password();