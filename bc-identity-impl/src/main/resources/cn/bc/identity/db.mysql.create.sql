-- 系统标识相关模块

-- 参与者的扩展属性
create table BC_IDENTITY_ACTOR_DETAIL (
    ID int NOT NULL auto_increment,
    CREATEDATE datetime COMMENT '创建时间',
    FIRST_NAME varchar(45) COMMENT 'user-姓氏',
    LAST_NAME varchar(45) COMMENT 'user-名字',
    SEX int(1) COMMENT 'user-性别：1-男,2-女',
    primary key (ID)
) COMMENT='参与者的扩展属性';

-- 参与者
create table BC_IDENTITY_ACTOR (
    ID int NOT NULL auto_increment,
    UID varchar(36) NOT NULL COMMENT '全局标识',
    TYPE_ int(1) NOT NULL COMMENT '类型：1-用户,2-单位,3-部门,4-岗位',
    STATUS_ int(1) NOT NULL COMMENT '状态：0-已禁用,1-启用中,2-已删除',
    INNER_ int(1) NOT NULL COMMENT '是否为内置对象:0-否,1-是',
    CODE varchar(100) NOT NULL COMMENT '编码',
    NAME varchar(255) NOT NULL COMMENT '名称',
    ORDER_ varchar(100) COMMENT '同类参与者之间的排序号',
    EMAIL varchar(255) COMMENT '邮箱',
    PHONE varchar(255) COMMENT '联系电话',
    DETAIL_ID int COMMENT '扩展表的ID',
    primary key (ID)
) COMMENT='参与者(代表一个人或组织，组织也可以是单位、部门、岗位、团队等)';
ALTER TABLE BC_IDENTITY_ACTOR ADD CONSTRAINT FK_ACTOR_ACTORDETAIL FOREIGN KEY (DETAIL_ID) 
	REFERENCES BC_IDENTITY_ACTOR_DETAIL (ID) ON DELETE CASCADE ON UPDATE RESTRICT;

-- 参与者之间的关联关系
create table BC_IDENTITY_ACTOR_RELATION (
    TYPE_ int(2) NOT NULL COMMENT '关联类型',
    MASTER_ID int NOT NULL COMMENT '主控方ID',
   	FOLLOWER_ID int NOT NULL COMMENT '从属方ID',
    ORDER_ varchar(100) COMMENT '从属方之间的排序号',
    primary key (MASTER_ID,FOLLOWER_ID,TYPE_)
) COMMENT='参与者之间的关联关系';
ALTER TABLE BC_IDENTITY_ACTOR_RELATION ADD CONSTRAINT FK_ARMASTER_ACTOR FOREIGN KEY (MASTER_ID) 
	REFERENCES BC_IDENTITY_ACTOR (ID);
ALTER TABLE BC_IDENTITY_ACTOR_RELATION ADD CONSTRAINT FK_ARFOLLOWER_ACTOR FOREIGN KEY (FOLLOWER_ID) 
	REFERENCES BC_IDENTITY_ACTOR (ID);

-- 职务
create table BC_IDENTITY_DUTY (
    ID int NOT NULL auto_increment,
    UID varchar(36) COMMENT '全局标识',
    STATUS_ int(1) NOT NULL COMMENT '状态：0-已禁用,1-启用中,2-已删除',
    INNER_ int(1) NOT NULL COMMENT '是否为内置对象:0-否,1-是',
    CODE varchar(100) NOT NULL COMMENT '编码',
    NAME varchar(255) NOT NULL COMMENT '名称',
    primary key (ID)
) COMMENT='职务';

-- 标识生成器
CREATE TABLE BC_IDENTITY_IDGENERATOR (
  TYPE_ VARCHAR(45) NOT NULL COMMENT '分类',
  VALUE INT NOT NULL COMMENT '当前值',
  FORMAT VARCHAR(45) COMMENT '格式模板,如“case-${V}”、“${T}-${V}”,V代表value的值，T代表type_的值' ,
  PRIMARY KEY (TYPE_)
) COMMENT = '标识生成器,用于生成主键或唯一编码用';
