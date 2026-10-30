
-- =====================================================
-- FLOWBANK - ESTRUTURA DO BANCO DE DADOS
-- MySQL 8.0
--
-- ATENCAO:
-- Este arquivo documenta a estrutura final do banco.
-- Executar somente em um banco vazio.
-- Nao executar sobre o banco existente, pois as tabelas
-- ja foram criadas e possuem dados.
-- =====================================================

CREATE DATABASE flowbank
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE flowbank;

-- =====================================================
-- 1. TABELA DE USUARIOS
-- =====================================================

CREATE TABLE usuarios (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    celular VARCHAR(20) NOT NULL,
    data_nascimento DATE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    perfil ENUM('CLIENTE', 'ADMIN') NOT NULL DEFAULT 'CLIENTE',

    PRIMARY KEY (id),
    UNIQUE KEY email (email)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;

-- =====================================================
-- 2. TABELA DE CONTAS
-- =====================================================

CREATE TABLE contas (
    id INT NOT NULL AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    saldo DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    numero_conta VARCHAR(20) NOT NULL,
    agencia VARCHAR(10) NOT NULL,
    status ENUM('ATIVA', 'BLOQUEADA', 'ENCERRADA')
        NOT NULL DEFAULT 'ATIVA',
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    UNIQUE KEY usuario_id (usuario_id),
    UNIQUE KEY uq_contas_numero (numero_conta),

    CONSTRAINT fk_contas_usuarios
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE RESTRICT

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;

-- =====================================================
-- 3. TABELA DE TRANSACOES
-- =====================================================

CREATE TABLE transacoes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    conta_id INT NOT NULL,
    tipo ENUM('ENTRADA', 'SAIDA') NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    KEY idx_transacoes_conta_criado (conta_id, criado_em),

    CONSTRAINT chk_transacoes_valor
        CHECK (valor > 0),

    CONSTRAINT fk_transacoes_contas
        FOREIGN KEY (conta_id)
        REFERENCES contas(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;
