DROP TABLE IF EXISTS omr_account;
create table omr_account
(
    account_id bigint GENERATED BY DEFAULT AS IDENTITY
        primary key,
    email      varchar(25) not null,
    nickname   varchar(25) not null,
    password   varchar(60) not null,
    constraint UK_aj94w8l7o0guff76320hxylt6
        unique (nickname),
    constraint UK_ok9jgxwvprib5g5mqyil9q8o8
        unique (email)
);

create table omr_character
(
    character_id           bigint GENERATED BY DEFAULT AS IDENTITY
        primary key,
    account_id             bigint      null,
    character_se_number    int         not null,
    name                   varchar(12) not null,
    playable_class         int         not null,
    specialization         int         not null,
    level                  int         not null,
    equipped_item_level    int         not null,
    average_item_lvel      int         not null,
    faction                varchar(10) not null,
    gender                 varchar(10) not null,
    slug                   int         not null,
    race                   int         not null,
    last_crawled_at        datetime(6) not null,
    constraint UK_gt8m76k66drao1gnlnc4vh5fl
        unique (character_se_number),
    constraint FKivmh71dp7mq8nhcsopj762c62
        foreign key (account_id) references omr_account (account_id)
);

create table omr_party_info
(
    party_id         bigint GENERATED BY DEFAULT AS IDENTITY
        primary key,
    contents         varchar(1000) not null,
    difficulty       varchar(6)    not null,
    instance_name    varchar(50)   not null,
    member_capacity  varchar(5)    not null,
    recruit_until    datetime(6)   not null,
    required_members int           not null,
    slug             varchar(10)   null,
    start_at         datetime(6)   not null,
    subject          varchar(50)   not null,
    times            int           not null,
    account_id       bigint        null,
    constraint FKkf6ak9kigojpdpf9larptkpmn
        foreign key (account_id) references omr_account (account_id)
);

create table omr_raid_detail
(
    detail_id       bigint GENERATED BY DEFAULT AS IDENTITY
        primary key,
    boss_name       varchar(100) not null comment '네임드명',
    boss_id         bigint       not null comment '네임드 고유 ID',
    completed_count int          not null comment '총 킬수',
    difficulty      bigint       not null comment '난이도',
    expansion_name  varchar(100) not null comment '확장팩명',
    expansion_id    bigint       not null comment '확장팩 고유 ID',
    instance_name   varchar(100) not null comment '공격대명',
    instance_id     bigint       not null comment '공격대 고유 ID',
    last_crawled_at datetime(6)  not null comment '최종수집 Timestamp',
    last_killed_at  datetime(6)  not null comment '최종처치 Timestamp',
    character_id    bigint       null,
    constraint FKhn7d215stvsje247blrimrj07
        foreign key (character_id) references omr_character (character_id)
);

