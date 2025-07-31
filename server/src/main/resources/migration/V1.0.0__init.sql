DROP TABLE IF EXISTS app;
CREATE TABLE app (
  id UUID PRIMARY KEY,
  name VARCHAR NOT NULL DEFAULT '',
  app_id VARCHAR NOT NULL DEFAULT '',
  secret VARCHAR NOT NULL DEFAULT '',
  description VARCHAR NOT NULL DEFAULT '',
  version VARCHAR NOT NULL DEFAULT '',
  index_url VARCHAR NOT NULL DEFAULT '',
  status VARCHAR NOT NULL DEFAULT 'ACTIVE',
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at BIGINT NOT NULL DEFAULT 0,
  created_by VARCHAR NOT NULL DEFAULT '',
  created_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
  updated_by VARCHAR DEFAULT '',
  updated_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX uk_app_name ON app (name) WHERE is_deleted = TRUE;
CREATE UNIQUE INDEX uk_app_appid ON app (app_id) WHERE is_deleted = TRUE;
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

DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
  id UUID PRIMARY KEY,
  username VARCHAR NOT NULL DEFAULT '',
  password VARCHAR NOT NULL DEFAULT '',
  email VARCHAR NOT NULL DEFAULT '',
  phone VARCHAR NOT NULL DEFAULT '',
  status VARCHAR NOT NULL DEFAULT 'NORMAL',
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at BIGINT NOT NULL DEFAULT 0,
  created_by VARCHAR NOT NULL DEFAULT '',
  created_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
  updated_by VARCHAR DEFAULT '',
  updated_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX uk_user_username ON "user" (username) WHERE is_deleted = TRUE;
CREATE UNIQUE INDEX uk_user_email ON "user" (email) WHERE is_deleted = TRUE;
CREATE UNIQUE INDEX uk_user_phone ON "user" (phone) WHERE is_deleted = TRUE;
COMMENT ON COLUMN "user".username IS '用户名';
COMMENT ON COLUMN "user".password IS '密码';
COMMENT ON COLUMN "user".email IS '邮箱';
COMMENT ON COLUMN "user".phone IS '手机号';
COMMENT ON COLUMN "user".is_deleted IS '是否删除';
COMMENT ON COLUMN "user".deleted_at IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN "user".created_by IS '创建人';
COMMENT ON COLUMN "user".created_time IS '创建时间';
COMMENT ON COLUMN "user".updated_by IS '最后修改人';
COMMENT ON COLUMN "user".updated_time IS '最后修改时间';

DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id UUID PRIMARY KEY,
  app_id BIGINT NOT NULL,
  name VARCHAR NOT NULL DEFAULT '',
  code VARCHAR NOT NULL DEFAULT '',
  description VARCHAR NOT NULL DEFAULT '',
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at BIGINT NOT NULL DEFAULT 0,
  created_by VARCHAR NOT NULL DEFAULT '',
  created_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
  updated_by VARCHAR DEFAULT '',
  updated_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX uk_role_appid_code ON role (app_id, code) WHERE is_deleted = TRUE;
CREATE UNIQUE INDEX uk_role_appid_name ON role (app_id, name) WHERE is_deleted = TRUE;
COMMENT ON COLUMN role.app_id IS '应用ID';
COMMENT ON COLUMN role.name IS '角色名称';
COMMENT ON COLUMN role.code IS '角色编码';
COMMENT ON COLUMN role.description IS '角色描述';
COMMENT ON COLUMN role.is_deleted IS '是否删除';
COMMENT ON COLUMN role.deleted_at IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN role.created_by IS '创建人';
COMMENT ON COLUMN role.created_time IS '创建时间';
COMMENT ON COLUMN role.updated_by IS '最后修改人';
COMMENT ON COLUMN role.updated_time IS '最后修改时间';

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,

  CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id)
);
COMMENT ON COLUMN user_role.user_id IS '用户ID';
COMMENT ON COLUMN user_role.role_id IS '角色ID';

DROP TABLE IF EXISTS permission;
CREATE TABLE permission (
  id UUID PRIMARY KEY,
  app_id BIGINT NOT NULL,
  name VARCHAR NOT NULL DEFAULT '',
  code VARCHAR NOT NULL DEFAULT '',
  description VARCHAR NOT NULL DEFAULT '',
  url VARCHAR NOT NULL DEFAULT '',
  parent_id BIGINT NOT NULL DEFAULT 0,
  parent_path VARCHAR NOT NULL DEFAULT '',
  sort DOUBLE PRECISION NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at BIGINT NOT NULL DEFAULT 0,
  created_by VARCHAR NOT NULL DEFAULT '',
  created_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
  updated_by VARCHAR DEFAULT '',
  updated_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
CREATE UNIQUE INDEX uk_permission_appid_code ON permission (app_id, code) WHERE is_deleted = TRUE;
COMMENT ON COLUMN permission.app_id IS '应用ID';
COMMENT ON COLUMN permission.name IS '权限名称';
COMMENT ON COLUMN permission.code IS '权限编码';
COMMENT ON COLUMN permission.description IS '权限描述';
COMMENT ON COLUMN permission.url IS '权限URL';
COMMENT ON COLUMN permission.parent_id IS '父权限ID';
COMMENT ON COLUMN permission.parent_path IS '父权限路径';
COMMENT ON COLUMN permission.sort IS '排序';
COMMENT ON COLUMN permission.is_deleted IS '是否删除';
COMMENT ON COLUMN permission.deleted_at IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN permission.created_by IS '创建人';
COMMENT ON COLUMN permission.created_time IS '创建时间';
COMMENT ON COLUMN permission.updated_by IS '最后修改人';
COMMENT ON COLUMN permission.updated_time IS '最后修改时间';

DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission (
  role_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,

  CONSTRAINT pk_role_permission PRIMARY KEY (role_id, permission_id)
);
COMMENT ON COLUMN role_permission.role_id IS '角色ID';
COMMENT ON COLUMN role_permission.permission_id IS '权限ID';