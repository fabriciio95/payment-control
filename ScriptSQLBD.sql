create database tp_db;

CREATE TABLE cri_crianca
(
cri_cod_crianca SERIAL PRIMARY KEY,
cri_nome VARCHAR(70) NOT NULL,
cri_idade INT(3) UNSIGNED NOT NULL,
cri_escola VARCHAR(70) NOT NULL,
cri_ano_escolar VARCHAR(50) NOT NULL,
cri_responsavel VARCHAR(100) NOT NULL,
cri_responsavel2 VARCHAR(100) DEFAULT '-',
cri_periodo VARCHAR(50) NOT NULL,
cri_telefone BIGINT(15) UNSIGNED NOT NULL,
cri_telefone2 BIGINT(15) UNSIGNED
);

CREATE TABLE pag_pagamento
(
pag_cod_pagamento SERIAL PRIMARY KEY,
pag_valor_pago DECIMAL(15,2) UNSIGNED NOT NULL,
pag_data DATETIME(6) NOT NULL,
cri_cod_crianca BIGINT UNSIGNED NOT NULL,
FOREIGN KEY(cri_cod_crianca) REFERENCES cri_crianca(cri_cod_crianca)
);

CREATE TABLE senha
(
senha_id SERIAL PRIMARY KEY,
senha VARCHAR(8) NOT NULL
);
