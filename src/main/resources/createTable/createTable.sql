CREATE TABLE `sys_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(225) DEFAULT NULL COMMENT '密码',
  `email` varchar(225) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

CREATE TABLE `sys_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role` varchar(50) NOT NULL COMMENT '角色',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名',
  `modules` text COMMENT '权限',
  `describe` text COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名',
  `url` varchar(200) DEFAULT NULL COMMENT 'url',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类id',
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `remark` text COMMENT '描述',
  `icon` varchar(30) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单';

CREATE TABLE `r_user_role` (
  `user_id` int(10) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(10) DEFAULT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关系表';

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

drop table if exists goods_class;

drop table if exists sys_goods;

drop table if exists up_class;

/*==============================================================*/
/* Table: goods_class                                           */
/*==============================================================*/
create table goods_class
(
   goodsClass           varchar(100) not null,
   upClassName          varchar(100),
   goodsName            varchar(100),
   primary key (goodsClass)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*==============================================================*/
/* Table: sys_goods                                             */
/*==============================================================*/
create table sys_goods
(
   goodsID              int not null auto_increment,
   goodsClass           varchar(100) not null,
   goodsPath            varchar(500) not null,
   goodsFilename        varchar(200) not null,
   uploadUser           bigint not null,
   markUserID           bigint not null,
   primary key (goodsID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*==============================================================*/
/* Table: up_class                                              */
/*==============================================================*/
create table up_class
(
   upClassName          varchar(100) not null,
   upClassContent       varchar(500),
   primary key (upClassName)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table goods_class add constraint FK_Relationship_4 foreign key (upClassName)
      references up_class (upClassName) on delete restrict on update restrict;

alter table sys_goods add constraint FK_Mark foreign key (goodsClass)
      references goods_class (goodsClass) on delete restrict on update restrict;
