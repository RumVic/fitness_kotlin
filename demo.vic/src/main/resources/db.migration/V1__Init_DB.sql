CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE IF NOT EXISTS app.user_fitness
(
    id uuid NOT NULL,
    activation_code character varying(255),
    dt_create bigint,
    dt_update bigint,
    login character varying(255) ,
    password character varying(255) ,
    role character varying(255),
    status character varying(255),
    username character varying(255),
    CONSTRAINT user_fitness_pkey PRIMARY KEY (id),
    CONSTRAINT uk_iexs7prkdjij3n0b8csfpm32t UNIQUE (login)
);


CREATE TABLE IF NOT EXISTS app.profile_fitness
(
    id uuid NOT NULL,
    birthday timestamp without time zone,
    dt_create bigint,
    dt_update bigint,
    gender character varying(255),
    height double precision NOT NULL,
    lifestyle character varying(255),
    target_weight double precision NOT NULL,
    weight double precision NOT NULL,
    user_id uuid,
    CONSTRAINT profile_fitness_pkey PRIMARY KEY (id),
    CONSTRAINT fk4mekhdg2wnln08f77pl96jur FOREIGN KEY (user_id)
        REFERENCES app.user_fitness (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS app.product_fitness
    (
    id uuid NOT NULL,
    calories double precision NOT NULL,
    carbohydrates double precision NOT NULL,
    created_by_role character varying(255),
    dt_create bigint,
    dt_update bigint,
    fats double precision NOT NULL,
    proteins double precision NOT NULL,
    title character varying(255),
    weight double precision NOT NULL,
    CONSTRAINT product_fitness_pkey PRIMARY KEY (id)
    );



CREATE TABLE IF NOT EXISTS app.dish_fitness
(
    id uuid NOT NULL,
    create_by_role character varying(255),
    dt_create bigint,
    dt_update bigint,
    title character varying(255),
    CONSTRAINT dish_fitness_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS app.diary_food_fitness
(
    id uuid NOT NULL,
    dt_create bigint,
    dt_supply bigint,
    dt_update bigint,
    profile uuid,
    weight_dish double precision,
    weight_product double precision,
    dish_id uuid,
    product_id uuid,
    CONSTRAINT diary_food_fitness_pkey PRIMARY KEY (id),
    CONSTRAINT fk8a6o1a0cymmd0hqgfxjtbp3vg FOREIGN KEY (product_id)
        REFERENCES app.product_fitness (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fka98nproi6misbreau5x98m0lt FOREIGN KEY (dish_id)
        REFERENCES app.dish_fitness (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);



CREATE TABLE IF NOT EXISTS app.composition_dish_fitness
(
    id uuid NOT NULL,
    dish uuid,
    dt_create bigint,
    dt_update bigint,
    title character varying(255),
    weight double precision NOT NULL,
    product_id uuid,
    CONSTRAINT composition_dish_fitness_pkey PRIMARY KEY (id),
    CONSTRAINT fkddr4sj2enb8n3hodsma7pt64h FOREIGN KEY (product_id)
        REFERENCES app.product_fitness (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS app.audit_fitness
(
    id uuid NOT NULL,
    dt_create bigint,
    dt_update bigint,
    text character varying(255),
    type character varying(255),
    uid character varying(255),
    user_id uuid,
    CONSTRAINT audit_fitness_pkey PRIMARY KEY (id),
    CONSTRAINT fkuw1bpygmwuo2lijig5qid718 FOREIGN KEY (user_id)
        REFERENCES app.user_fitness (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS app.composition_dish_fitness
(
    id uuid NOT NULL,
    dish uuid,
    dt_create bigint,
    dt_update bigint,
    title character varying(255),
    weight double precision NOT NULL,
    product_id uuid,
    CONSTRAINT composition_dish_fitness_pkey PRIMARY KEY (id),
    CONSTRAINT fkddr4sj2enb8n3hodsma7pt64h FOREIGN KEY (product_id)
        REFERENCES app.product_fitness (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS app.audit_fitness
(
    id uuid NOT NULL,
    dt_create bigint,
    dt_update bigint,
    text character varying(255),
    type character varying(255),
    uid character varying(255),
    user_id uuid,
    CONSTRAINT audit_fitness_pkey PRIMARY KEY (id),
    CONSTRAINT fkuw1bpygmwuo2lijig5qid718 FOREIGN KEY (user_id)
        REFERENCES app.user_fitness (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
