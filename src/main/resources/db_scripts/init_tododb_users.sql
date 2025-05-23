DROP DATABASE IF EXISTS tododb;
DROP USER IF EXISTS `todoadmin`@`%`;
DROP USER IF EXISTS `theuser`@`%`;
CREATE DATABASE IF NOT EXISTS tododb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `todoadmin`@`%` IDENTIFIED BY 'todoadmin';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `tododb`.* TO `todoadmin`@`%` WITH GRANT OPTION;
-- GRANT ALL ON `tododb`.* TO `todoadmin`@`%` WITH GRANT OPTION;
CREATE USER IF NOT EXISTS `theuser`@`%` IDENTIFIED BY 'theuser';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `tododb`.* TO `theuser`@`%`;
-- GRANT ALL ON `tododb`.* TO `theuser`@`%` WITH GRANT OPTION;
FLUSH PRIVILEGES;