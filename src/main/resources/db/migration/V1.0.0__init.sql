DROP TYPE IF EXISTS app_status;
CREATE TYPE app_status AS ENUM ('active', 'inactive');
DROP TABLE IF EXISTS app;
CREATE TABLE app (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY,
  name VARCHAR NOT NULL DEFAULT '',
  app_id VARCHAR NOT NULL DEFAULT '',
  secret VARCHAR NOT NULL DEFAULT '',
  description VARCHAR NOT NULL DEFAULT '',
  version VARCHAR NOT NULL DEFAULT '',
  index_url VARCHAR NOT NULL DEFAULT '',
  status app_status NOT NULL DEFAULT 'active',
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at BIGINT NOT NULL DEFAULT 0,
  created_by VARCHAR NOT NULL DEFAULT '',
  created_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
  updated_by VARCHAR DEFAULT '',
  updated_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),

  CONSTRAINT pk_app PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_name ON app (name) WHERE is_deleted = TRUE;
CREATE UNIQUE INDEX uk_appid ON app (app_id) WHERE is_deleted = TRUE;

COMMENT ON COLUMN app.name IS '应用名';
COMMENT ON COLUMN app.app_id IS '应用ID';
COMMENT ON COLUMN app.secret IS '应用密钥';
COMMENT ON COLUMN app.description IS '应用描述';
COMMENT ON COLUMN app.version IS '应用版本';
COMMENT ON COLUMN app.index_url IS '应用首页地址';
COMMENT ON COLUMN app.status IS '应用状态';
COMMENT ON COLUMN app.is_deleted IS '是否删除';
COMMENT ON COLUMN app.deleted_at IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN app.created_by IS '创建人';
COMMENT ON COLUMN app.created_time IS '创建时间';
COMMENT ON COLUMN app.updated_by IS '最后修改人';
COMMENT ON COLUMN app.updated_time IS '最后修改时间';