-- public.menuitem definition

-- Drop table

-- DROP TABLE public.menuitem;

CREATE TABLE public.menuitem (
                                 id varchar(50) NOT NULL,
                                 "name" varchar(30) NOT NULL,
                                 price numeric(10) NOT NULL,
                                 "type" varchar(10) NOT NULL,
                                 use_yn bool NOT NULL,
                                 create_id varchar(30) NULL,
                                 create_date timestamp NOT NULL,
                                 update_id varchar(30) NULL,
                                 update_date timestamp NOT NULL,
                                 CONSTRAINT menuitem_pk PRIMARY KEY (id)
);