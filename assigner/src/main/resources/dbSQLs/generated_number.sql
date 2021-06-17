create table if not exists GENERATED_NUMBER
(
    ID         LONG    not null,
    IDENTIFIER VARCHAR not null,
    VALUE      LONG    not null,
    constraint GENERATED_NUMBER_PK
        primary key (ID)
);

create unique index GENERATED_NUMBER_IDENTIFIER_UINDEX
    on GENERATED_NUMBER (IDENTIFIER);
