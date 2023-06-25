create
database db_blog;

create table tb_user
(
    username varchar(10) primary key,
    password varchar(15) not null,
    role     varchar(5)  not null
);

create table tb_post
(
    post_id     bigint auto_increment primary key,
    username    varchar(10)   not null,
    title       varchar(256)  not null,
    content     varchar(1024) not null,
    created_at  timestamp     not null,
    modified_at timestamp     not null,
    constraint fk_tb_user_tb_post foreign key (username) references tb_user (username)
);

create table tb_comment
(
    comment_id  bigint auto_increment primary key,
    post_id     bigint        not null,
    username    varchar(10)   not null,
    content     varchar(1024) not null,
    created_at  timestamp     not null,
    modified_at timestamp     not null,
    constraint fk_tb_post_tb_comment foreign key (post_id) references tb_post (post_id),
    constraint fk_tb_user_tb_comment foreign key (username) references tb_user (username)
);
