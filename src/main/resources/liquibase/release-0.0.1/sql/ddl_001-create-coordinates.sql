--liquibase formatted sql

--changeset khannanovni:ddl_001-create-coordinates logicalFilePath:/

CREATE SCHEMA IF NOT EXISTS ${robot.schemaName};

CREATE TABLE IF NOT EXISTS ${robot.schemaName}.coordinates
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    x  BIGINT                NOT NULL DEFAULT 0,
    y  BIGINT                NOT NULL DEFAULT 0
);

ALTER SEQUENCE IF EXISTS ${robot.schemaName}.coordinates_id_seq INCREMENT 50;