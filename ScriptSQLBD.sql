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
Desc cri_crianca;
SELECT cri.cri_nome, cri.cri_responsavel, pag.pag_data, pag.pag_valor_pago FROM cri_crianca cri INNER JOIN pag_pagamento pag ON cri.cri_cod_crianca = pag.cri_cod_crianca and LOWER(cri.cri_nome) LIKE LOWER(CONCAT('%','l','%')); 
SELECT cri.cri_nome, cri.cri_responsavel, cri.cri_responsavel2, cri.cri_telefone, cri.cri_telefone2, pag.pag_data, pag.pag_valor_pago FROM cri_crianca cri LEFT JOIN pag_pagamento pag ON cri.cri_cod_crianca = pag.cri_cod_crianca and MONTH(pag.pag_data) = '4';
UPDATE pag_pagamento SET pag_valor_pago = 230,  pag_data = '2020-03-14',  cri_cod_crianca = 4 WHERE pag_cod_pagamento = 1;

SELECT cri.cri_cod_crianca, cri.cri_nome, cri.cri_responsavel, cri.cri_responsavel2, pag.pag_cod_pagamento, pag.pag_data, pag.pag_valor_pago, pag.cri_cod_crianca FROM cri_crianca cri INNER JOIN pag_pagamento pag ON cri.cri_cod_crianca = pag.cri_cod_crianca AND (LOWER(cri.cri_responsavel) LIKE LOWER(CONCAT('%','c','%')) OR  LOWER(cri.cri_responsavel2) LIKE LOWER(CONCAT('%','c','%')));
ALTER TABLE cri_crianca ADD COLUMN cri_telefone2 BIGINT(15) UNSIGNED;
ALTER TABLE cri_crianca ADD COLUMN cri_responsavel2 VARCHAR(100) DEFAULT '-';
ALTER TABLE cri_crianca ADD COLUMN cri_idade INT(3) UNSIGNED NOT NULL; 

SELECT * FROM cri_crianca cri WHERE LOWER(cri.cri_responsavel) LIKE LOWER(CONCAT('%','mil','%')) OR LOWER(cri.cri_responsavel2) LIKE LOWER(CONCAT('%','mil','%'))  ORDER BY cri_responsavel;
desc pag_pagamento;
CREATE TABLE pag_pagamento
(
pag_cod_pagamento SERIAL PRIMARY KEY,
pag_valor_pago DECIMAL(15,2) UNSIGNED NOT NULL,
pag_data DATETIME NOT NULL,
cri_cod_crianca BIGINT UNSIGNED NOT NULL,
FOREIGN KEY(cri_cod_crianca) REFERENCES cri_crianca(cri_cod_crianca)
);

ALTER TABLE pag_pagamento CHANGE COLUMN pag_data pag_data DATETIME(6) NOT NULL;

CREATE TABLE senha
(
senha_id SERIAL PRIMARY KEY,
senha VARCHAR(8) NOT NULL
);
