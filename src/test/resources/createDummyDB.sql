-- ============================================================
--	Create schema
-- ============================================================
CREATE SCHEMA test;

-- ============================================================
--	Table: DUMMY
-- ============================================================
CREATE TABLE Dummy (
	id		INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name	VARCHAR(45)	NOT NULL
);

-- ============================================================
--	Data: Dummy
-- ============================================================
INSERT INTO Dummy (id, name) VALUES (1, 'aba');
INSERT INTO Dummy (id, name) VALUES (2, 'ldr');

-- ============================================================
--	User creation
-- ============================================================
CREATE USER IF NOT EXISTS aba PASSWORD 'ldr';
GRANT INSERT, SELECT, UPDATE, DELETE ON Dummy TO aba;

commit;