/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50555
Source Host           : localhost:3306
Source Database       : syslink

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2017-12-08 11:39:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for action
-- ----------------------------
DROP TABLE IF EXISTS `action`;
CREATE TABLE `action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_userid` bigint(20) DEFAULT NULL,
  `act_user_name` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_unix` bigint(20) DEFAULT NULL,
  `is_private` bit(1) DEFAULT NULL,
  `op_type` int(11) DEFAULT NULL,
  `ref_name` varchar(255) DEFAULT NULL,
  `repoid` bigint(20) DEFAULT NULL,
  `repo_name` varchar(255) DEFAULT NULL,
  `repo_user_name` varchar(255) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of action
-- ----------------------------

-- ----------------------------
-- Table structure for auth
-- ----------------------------
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth` (
  `auth_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `auth_code` varchar(255) NOT NULL,
  `auth_name` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth
-- ----------------------------
INSERT INTO `auth` VALUES ('25', null, 'management_org', '组织管理', '2017-10-16 23:25:39', '', null);
INSERT INTO `auth` VALUES ('26', null, 'management_user', '用户管理', '2017-10-16 23:26:13', '', null);
INSERT INTO `auth` VALUES ('27', null, 'management_role', '角色管理', '2017-10-16 23:26:32', '', null);
INSERT INTO `auth` VALUES ('28', null, 'management_auth', '权限管理', '2017-10-16 23:28:06', '', null);
INSERT INTO `auth` VALUES ('29', null, 'management_log', '日志管理', '2017-10-16 23:29:39', '', null);
INSERT INTO `auth` VALUES ('32', '26', 'management_user_list', '用户列表', '2017-12-07 13:18:11', null, null);
INSERT INTO `auth` VALUES ('33', '25', 'management_org_list', '组织列表', '2017-10-17 11:41:01', null, null);
INSERT INTO `auth` VALUES ('34', '27', 'management_role_list', '角色列表', '2017-10-17 11:41:35', null, null);
INSERT INTO `auth` VALUES ('35', '28', 'management_auth_list', '权限列表', '2017-10-17 11:41:53', null, null);
INSERT INTO `auth` VALUES ('36', '29', 'management_log_list', '日志列表', '2017-10-17 11:42:18', null, null);
INSERT INTO `auth` VALUES ('40', null, 'management_repo', '仓库管理', '2017-11-01 08:56:53', '', null);
INSERT INTO `auth` VALUES ('41', '40', 'management_repo_add', '新建仓库', '2017-11-01 08:57:22', null, null);
INSERT INTO `auth` VALUES ('42', null, 'management_model', '模型管理', '2017-11-22 16:06:54', '对模型的上传和下载等进行管理', null);
INSERT INTO `auth` VALUES ('44', '42', 'management_model_delete', '删除模型', '2017-12-07 13:14:29', null, null);

-- ----------------------------
-- Table structure for cae_component
-- ----------------------------
DROP TABLE IF EXISTS `cae_component`;
CREATE TABLE `cae_component` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon_file` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `requirements` varchar(255) DEFAULT NULL,
  `runos` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cae_component
-- ----------------------------

-- ----------------------------
-- Table structure for cae_file
-- ----------------------------
DROP TABLE IF EXISTS `cae_file`;
CREATE TABLE `cae_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cae_component_id` bigint(20) DEFAULT NULL,
  `delimiters_type` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `template_file_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cae_file
-- ----------------------------

-- ----------------------------
-- Table structure for cae_variable
-- ----------------------------
DROP TABLE IF EXISTS `cae_variable`;
CREATE TABLE `cae_variable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cae_file_id` bigint(20) DEFAULT NULL,
  `delimiters` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `enum_aliases` varchar(255) DEFAULT NULL,
  `enum_values` varchar(255) DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `fortran_format` varchar(255) DEFAULT NULL,
  `max` varchar(255) DEFAULT NULL,
  `min` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_bookmark` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `units` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `value_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cae_variable
-- ----------------------------

-- ----------------------------
-- Table structure for component
-- ----------------------------
DROP TABLE IF EXISTS `component`;
CREATE TABLE `component` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `modification` varchar(255) DEFAULT NULL,
  `type` varchar(128) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `current_model_id` bigint(20) DEFAULT NULL,
  `model_id` bigint(20) NOT NULL,
  `name` varchar(128) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `parent_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of component
-- ----------------------------

-- ----------------------------
-- Table structure for depart
-- ----------------------------
DROP TABLE IF EXISTS `depart`;
CREATE TABLE `depart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of depart
-- ----------------------------
INSERT INTO `depart` VALUES ('1', '2017-10-25 10:57:21', '根部门', '航天X院', '0');

-- ----------------------------
-- Table structure for depart_copy
-- ----------------------------
DROP TABLE IF EXISTS `depart_copy`;
CREATE TABLE `depart_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of depart_copy
-- ----------------------------
INSERT INTO `depart_copy` VALUES ('1', '2017-10-25 10:57:21', '根部门', '航天X院', '0');
INSERT INTO `depart_copy` VALUES ('31', '2017-11-22 14:32:28', null, '科研计划处', '37');
INSERT INTO `depart_copy` VALUES ('37', '2017-11-24 14:11:05', null, '102所', '1');
INSERT INTO `depart_copy` VALUES ('39', '2017-11-24 14:16:00', null, '1', '37');
INSERT INTO `depart_copy` VALUES ('41', '2017-11-24 14:16:59', null, '1', '40');
INSERT INTO `depart_copy` VALUES ('42', '2017-11-27 10:13:27', '1', '1', '1');
INSERT INTO `depart_copy` VALUES ('43', '2017-11-28 09:07:37', null, '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', '31');
INSERT INTO `depart_copy` VALUES ('44', '2017-11-28 09:07:53', null, '测试', '43');

-- ----------------------------
-- Table structure for directory
-- ----------------------------
DROP TABLE IF EXISTS `directory`;
CREATE TABLE `directory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `absolute_address` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `name` varchar(128) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `relative_address` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of directory
-- ----------------------------
INSERT INTO `directory` VALUES ('33', null, null, null, '\0', null, '公有模型', '0', null, null);
INSERT INTO `directory` VALUES ('34', null, null, null, '\0', null, '我的模型', '0', null, null);

-- ----------------------------
-- Table structure for directory_auth
-- ----------------------------
DROP TABLE IF EXISTS `directory_auth`;
CREATE TABLE `directory_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `directory_id` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of directory_auth
-- ----------------------------

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `absolute_address` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `name` varchar(128) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `relative_address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=679 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for file_model
-- ----------------------------
DROP TABLE IF EXISTS `file_model`;
CREATE TABLE `file_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `absolute_address` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `name` varchar(128) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `relative_address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_model
-- ----------------------------

-- ----------------------------
-- Table structure for guser
-- ----------------------------
DROP TABLE IF EXISTS `guser`;
CREATE TABLE `guser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `allow_git_hook` bit(1) DEFAULT NULL,
  `allow_import_local` bit(1) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `avatar_email` varchar(255) DEFAULT NULL,
  `created_unix` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_admin` bit(1) DEFAULT NULL,
  `last_repo_visibility` bit(1) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `login_source` bigint(20) DEFAULT NULL,
  `login_type` int(11) DEFAULT NULL,
  `lower_name` varchar(255) NOT NULL,
  `max_repo_creation` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `num_followers` int(11) DEFAULT NULL,
  `num_following` int(11) DEFAULT NULL,
  `num_members` int(11) DEFAULT NULL,
  `num_repos` int(11) DEFAULT NULL,
  `num_stars` int(11) DEFAULT NULL,
  `num_teams` int(11) DEFAULT NULL,
  `passwd` varchar(255) DEFAULT NULL,
  `prohibit_login` bit(1) DEFAULT NULL,
  `rands` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `updated_unix` bigint(20) DEFAULT NULL,
  `use_custom_avatar` bit(1) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_afg8k5uydepyv03jsb8omflce` (`lower_name`),
  UNIQUE KEY `UK_ksheljsb7faccb9c5otrncfc8` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of guser
-- ----------------------------

-- ----------------------------
-- Table structure for learn_resouce
-- ----------------------------
DROP TABLE IF EXISTS `learn_resouce`;
CREATE TABLE `learn_resouce` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of learn_resouce
-- ----------------------------

-- ----------------------------
-- Table structure for loginstate
-- ----------------------------
DROP TABLE IF EXISTS `loginstate`;
CREATE TABLE `loginstate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `last_login_ip` varchar(255) DEFAULT NULL,
  `login_count` int(11) DEFAULT NULL,
  `now_login_date` datetime DEFAULT NULL,
  `now_login_ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2145 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of loginstate
-- ----------------------------

-- ----------------------------
-- Table structure for model
-- ----------------------------
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `extends` varchar(128) DEFAULT NULL,
  `import` varchar(128) DEFAULT NULL,
  `classes` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `diagram_svg_path` varchar(256) DEFAULT NULL,
  `directory_id` bigint(20) DEFAULT NULL,
  `discription` varchar(256) DEFAULT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  `icon_svg_path` varchar(256) DEFAULT NULL,
  `info_text_path` varchar(256) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `model_file_path` varchar(256) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `scope` bit(1) NOT NULL,
  `text_info` varchar(2000) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of model
-- ----------------------------

-- ----------------------------
-- Table structure for model_auth
-- ----------------------------
DROP TABLE IF EXISTS `model_auth`;
CREATE TABLE `model_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mode` smallint(6) NOT NULL,
  `model_id` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `node_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of model_auth
-- ----------------------------

-- ----------------------------
-- Table structure for node_history
-- ----------------------------
DROP TABLE IF EXISTS `node_history`;
CREATE TABLE `node_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `node_name` varchar(255) DEFAULT NULL,
  `sequence` varchar(255) DEFAULT NULL,
  `template_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of node_history
-- ----------------------------

-- ----------------------------
-- Table structure for operationlog
-- ----------------------------
DROP TABLE IF EXISTS `operationlog`;
CREATE TABLE `operationlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `user_ip` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2787 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operationlog
-- ----------------------------

-- ----------------------------
-- Table structure for repository
-- ----------------------------
DROP TABLE IF EXISTS `repository`;
CREATE TABLE `repository` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `allow_public_issues` bit(1) DEFAULT NULL,
  `allow_public_wiki` bit(1) DEFAULT NULL,
  `created_unix` bigint(20) DEFAULT NULL,
  `default_branch` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `enable_external_tracker` bit(1) DEFAULT NULL,
  `enable_external_wiki` bit(1) DEFAULT NULL,
  `enable_issues` bit(1) DEFAULT NULL,
  `enable_pulls` bit(1) DEFAULT NULL,
  `enable_wiki` bit(1) DEFAULT NULL,
  `external_tracker_format` varchar(255) DEFAULT NULL,
  `external_tracker_style` varchar(255) DEFAULT NULL,
  `external_trackerurl` varchar(255) DEFAULT NULL,
  `external_wikiurl` varchar(255) DEFAULT NULL,
  `forkid` bigint(20) DEFAULT NULL,
  `is_bare` bit(1) DEFAULT NULL,
  `is_fork` bit(1) DEFAULT NULL,
  `is_mirror` bit(1) DEFAULT NULL,
  `is_private` bit(1) DEFAULT NULL,
  `lower_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `num_closed_issues` int(11) DEFAULT NULL,
  `num_closed_milestones` int(11) DEFAULT NULL,
  `num_closed_pulls` int(11) DEFAULT NULL,
  `num_forks` int(11) DEFAULT NULL,
  `num_issues` int(11) DEFAULT NULL,
  `num_milestones` int(11) DEFAULT NULL,
  `num_pulls` int(11) DEFAULT NULL,
  `num_stars` int(11) DEFAULT NULL,
  `num_watches` int(11) DEFAULT NULL,
  `ownerid` bigint(20) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `updated_unix` bigint(20) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3vtgpl8ih88ct50g0c2wrxjro` (`lower_name`),
  UNIQUE KEY `UK_nwv70v9q5333ehd8ob5dgtq22` (`ownerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of repository
-- ----------------------------

-- ----------------------------
-- Table structure for review_flow_instance
-- ----------------------------
DROP TABLE IF EXISTS `review_flow_instance`;
CREATE TABLE `review_flow_instance` (
  `instance_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `instance_name` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `model_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `template_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of review_flow_instance
-- ----------------------------

-- ----------------------------
-- Table structure for review_flow_template
-- ----------------------------
DROP TABLE IF EXISTS `review_flow_template`;
CREATE TABLE `review_flow_template` (
  `template_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `already_config` bit(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `default_template` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `template_name` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of review_flow_template
-- ----------------------------

-- ----------------------------
-- Table structure for review_model
-- ----------------------------
DROP TABLE IF EXISTS `review_model`;
CREATE TABLE `review_model` (
  `model_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `model_description` varchar(255) DEFAULT NULL,
  `model_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of review_model
-- ----------------------------

-- ----------------------------
-- Table structure for review_msg
-- ----------------------------
DROP TABLE IF EXISTS `review_msg`;
CREATE TABLE `review_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `from_user_id` bigint(20) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `to_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of review_msg
-- ----------------------------

-- ----------------------------
-- Table structure for review_node
-- ----------------------------
DROP TABLE IF EXISTS `review_node`;
CREATE TABLE `review_node` (
  `node_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `node_name` varchar(255) DEFAULT NULL,
  `sequence` varchar(255) DEFAULT NULL,
  `template_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of review_node
-- ----------------------------

-- ----------------------------
-- Table structure for review_node_instance
-- ----------------------------
DROP TABLE IF EXISTS `review_node_instance`;
CREATE TABLE `review_node_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `instance_id` bigint(20) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `node_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of review_node_instance
-- ----------------------------

-- ----------------------------
-- Table structure for review_success
-- ----------------------------
DROP TABLE IF EXISTS `review_success`;
CREATE TABLE `review_success` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `package_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of review_success
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '2017-10-13 11:16:52', '拥有系统所有权限', '系统管理员', null, null);
INSERT INTO `role` VALUES ('2', '2017-11-22 15:21:04', '', '安全管理员', null, null);
INSERT INTO `role` VALUES ('3', '2017-11-22 15:55:05', '负责系统审计，如日志追溯等', '审计管理员', null, null);

-- ----------------------------
-- Table structure for role_auth
-- ----------------------------
DROP TABLE IF EXISTS `role_auth`;
CREATE TABLE `role_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=538 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_auth
-- ----------------------------
INSERT INTO `role_auth` VALUES ('524', '25', '1');
INSERT INTO `role_auth` VALUES ('525', '33', '1');
INSERT INTO `role_auth` VALUES ('526', '26', '1');
INSERT INTO `role_auth` VALUES ('527', '32', '1');
INSERT INTO `role_auth` VALUES ('528', '27', '1');
INSERT INTO `role_auth` VALUES ('529', '34', '1');
INSERT INTO `role_auth` VALUES ('530', '28', '1');
INSERT INTO `role_auth` VALUES ('531', '35', '1');
INSERT INTO `role_auth` VALUES ('532', '29', '1');
INSERT INTO `role_auth` VALUES ('533', '36', '1');
INSERT INTO `role_auth` VALUES ('534', '40', '1');
INSERT INTO `role_auth` VALUES ('535', '41', '1');
INSERT INTO `role_auth` VALUES ('536', '42', '1');
INSERT INTO `role_auth` VALUES ('537', '44', '1');

-- ----------------------------
-- Table structure for simulation
-- ----------------------------
DROP TABLE IF EXISTS `simulation`;
CREATE TABLE `simulation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `deviation` int(11) DEFAULT NULL,
  `model_id` bigint(20) DEFAULT NULL,
  `model_version` varchar(255) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `result_file_path` varchar(255) DEFAULT NULL,
  `sim_step` double DEFAULT NULL,
  `template_file_url` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of simulation
-- ----------------------------

-- ----------------------------
-- Table structure for simulation_result
-- ----------------------------
DROP TABLE IF EXISTS `simulation_result`;
CREATE TABLE `simulation_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `algorithm` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `model_id` bigint(20) DEFAULT NULL,
  `model_version` varchar(255) DEFAULT NULL,
  `sim_step` double DEFAULT NULL,
  `simu_name` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of simulation_result
-- ----------------------------

-- ----------------------------
-- Table structure for star
-- ----------------------------
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `repo_id` bigint(20) DEFAULT NULL,
  `uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of star
-- ----------------------------

-- ----------------------------
-- Table structure for user_auth
-- ----------------------------
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth_id` int(11) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=735 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_auth
-- ----------------------------
INSERT INTO `user_auth` VALUES ('719', '25', '1');
INSERT INTO `user_auth` VALUES ('720', '33', '1');
INSERT INTO `user_auth` VALUES ('721', '26', '1');
INSERT INTO `user_auth` VALUES ('722', '32', '1');
INSERT INTO `user_auth` VALUES ('723', '27', '1');
INSERT INTO `user_auth` VALUES ('724', '34', '1');
INSERT INTO `user_auth` VALUES ('725', '28', '1');
INSERT INTO `user_auth` VALUES ('726', '35', '1');
INSERT INTO `user_auth` VALUES ('727', '29', '1');
INSERT INTO `user_auth` VALUES ('728', '36', '1');
INSERT INTO `user_auth` VALUES ('729', '40', '1');
INSERT INTO `user_auth` VALUES ('730', '41', '1');
INSERT INTO `user_auth` VALUES ('731', '42', '1');
INSERT INTO `user_auth` VALUES ('732', '44', '1');

-- ----------------------------
-- Table structure for user_depart
-- ----------------------------
DROP TABLE IF EXISTS `user_depart`;
CREATE TABLE `user_depart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `depart_id` int(11) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2167 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_depart
-- ----------------------------
INSERT INTO `user_depart` VALUES ('2138', '1', '1');

-- ----------------------------
-- Table structure for user_permission
-- ----------------------------
DROP TABLE IF EXISTS `user_permission`;
CREATE TABLE `user_permission` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_permission
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for variable
-- ----------------------------
DROP TABLE IF EXISTS `variable`;
CREATE TABLE `variable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_input` int(11) DEFAULT NULL,
  `is_param` int(11) NOT NULL,
  `is_variable` int(11) DEFAULT NULL,
  `componnet_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `default_value` varchar(255) DEFAULT NULL,
  `lower_bound` varchar(255) DEFAULT NULL,
  `model_id` bigint(20) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `parent_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `units` varchar(255) DEFAULT NULL,
  `upper_bound` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=317 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of variable
-- ----------------------------

-- ----------------------------
-- Table structure for variable_value
-- ----------------------------
DROP TABLE IF EXISTS `variable_value`;
CREATE TABLE `variable_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `simulation_id` bigint(20) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `variable_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of variable_value
-- ----------------------------

-- ----------------------------
-- Table structure for watch
-- ----------------------------
DROP TABLE IF EXISTS `watch`;
CREATE TABLE `watch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `repoid` bigint(20) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of watch
-- ----------------------------
