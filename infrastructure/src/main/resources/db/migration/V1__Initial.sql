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

   budget_id VARCHAR(32) NOT NULL,
    CONSTRAINT fk_id_budget FOREIGN KEY(budget_id)
    REFERENCES budgets(id)
);

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