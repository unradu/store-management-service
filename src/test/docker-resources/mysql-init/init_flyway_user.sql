USE storedb;

-- Create application user
CREATE USER 'app_user'@'%' IDENTIFIED BY 'userpw';
GRANT SELECT, INSERT, UPDATE, DELETE ON storedb.* TO 'app_user'@'%';

-- Create Flyway user
CREATE USER 'flyway_user'@'%' IDENTIFIED BY 'flywaypw';
GRANT ALL PRIVILEGES ON storedb.* TO 'flyway_user'@'%';

FLUSH PRIVILEGES;