CREATE TABLE budgets(
   id CHAR(32) NOT NULL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   actual_value DECIMAL NOT NULL,
   max_value DECIMAL NOT NULL,
   created_at TIMESTAMP(6) NOT NULL,
   updated_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE categories(
   id CHAR(32) NOT NULL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   type VARCHAR(8) NOT NULL,
   actual_value DECIMAL NOT NULL,
   created_at TIMESTAMP(6) NOT NULL,
   updated_at TIMESTAMP(6) NOT NULL,

   budget_id VARCHAR(32),
    CONSTRAINT fk_id_budget FOREIGN KEY(budget_id)
    REFERENCES budgets(id)
);

CREATE TABLE budgets_history(
 id SERIAL NOT NULL PRIMARY KEY,
 budget_id CHAR(32) NOT NULL,
 month SMALLINT NOT NULL,
 year SMALLINT NOT NULL,
 actual_value DECIMAL NOT NULL,
 max_value DECIMAL NOT NULL,

 CONSTRAINT fk_id_budget_history FOREIGN KEY(budget_id)
    REFERENCES budgets(id)
);

CREATE UNIQUE INDEX budgets_history_month_day
    ON budgets_history (budget_id, month, year);

CREATE TABLE categories_history(
 id SERIAL NOT NULL PRIMARY KEY,
 category_id CHAR(32) NOT NULL,
 month SMALLINT NOT NULL,
 year SMALLINT NOT NULL,
 actual_value DECIMAL NOT NULL,

 CONSTRAINT fk_id_category_history FOREIGN KEY(category_id)
    REFERENCES categories(id)
);

CREATE UNIQUE INDEX categories_history_month_day
    ON categories_history (category_id, month, year);

CREATE TABLE expenses(
   id CHAR(32) NOT NULL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   description VARCHAR(4000),
   amount DECIMAL NOT NULL,
   created_at TIMESTAMP(6) NOT NULL,

   category_id VARCHAR(32) NOT NULL,
    CONSTRAINT fk_id_category FOREIGN KEY(category_id)
    REFERENCES categories(id)
);