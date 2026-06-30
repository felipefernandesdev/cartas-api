-- Cartas MAGO
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Bola de Fogo', 'Causa 6 de dano a qualquer alvo', 6, 0, 4, 'MAGIA', 'MAGO');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Arcano Inteligente', 'Compra 2 cartas', 0, 0, 3, 'MAGIA', 'MAGO');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Elemental da Agua', 'Criatura que congela ao atacar', 3, 6, 4, 'CRIATURA', 'MAGO');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Explosao Arcana', 'Causa 2 de dano a todos os inimigos', 2, 0, 3, 'MAGIA', 'MAGO');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Polimorfo', 'Transforma criatura em cordeiro 1/1', 1, 1, 4, 'CRIATURA', 'MAGO');

-- Cartas PALADINO
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Martelo da Justica', 'Causa 4 de dano e cura 2 vida', 4, 3, 5, 'CRIATURA', 'PALADINO');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Bencao de Protecao', 'Concede 2 de defesa a todas as criaturas', 0, 0, 3, 'MAGIA', 'PALADINO');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Recruta da Aldeia', 'Criatura barata para inicio de jogo', 1, 1, 1, 'CRIATURA', 'PALADINO');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Consagracao', 'Causa 2 de dano e cura 2 vida', 0, 0, 4, 'MAGIA', 'PALADINO');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Tirion Fordring', 'Criatura poderosa com escudo divino', 6, 6, 8, 'CRIATURA', 'PALADINO');

-- Cartas CACADOR
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Tiro Preciso', 'Causa 5 de dano a um alvo', 5, 2, 3, 'CRIATURA', 'CACADOR');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Rastreador', 'Criatura barata com instinto de caça', 2, 1, 1, 'CRIATURA', 'CACADOR');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Chuva de Flechas', 'Causa 3 de dano a todas as criaturas', 3, 0, 5, 'MAGIA', 'CACADOR');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Urso Espiritual', 'Criatura forte para meio de jogo', 4, 4, 4, 'CRIATURA', 'CACADOR');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Tiro Aimado', 'Causa 6 de dano a criatura', 6, 0, 5, 'MAGIA', 'CACADOR');

-- Cartas DRUIDA
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Forma de Urso', 'Concede 2 de ataque e defesa', 2, 2, 2, 'MAGIA', 'DRUIDA');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Raizes Retorcidas', 'Causa 3 de dano e cura 3 vida', 3, 0, 3, 'MAGIA', 'DRUIDA');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Urso Feroz', 'Criatura resistente da floresta', 4, 6, 6, 'CRIATURA', 'DRUIDA');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Enraizar', 'Paralisa criatura inimiga por 1 turno', 0, 0, 2, 'MAGIA', 'DRUIDA');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Cenarius', 'Invoca 2/2 com taunt', 5, 8, 9, 'CRIATURA', 'DRUIDA');

-- Cartas QUALQUER (Neutrais)
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Espada Lendaria', 'Arma comum a todas as classes', 3, 1, 2, 'CRIATURA', 'QUALQUER');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Goblin Engenheiro', 'Causa 1 de dano a cada jogador', 2, 1, 2, 'CRIATURA', 'QUALQUER');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Guardiao da Montanha', 'Criatura com taunt para defesa', 2, 4, 3, 'CRIATURA', 'QUALQUER');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Assassino Sombrio', 'Elimina criatura com 4+ de ataque', 4, 2, 5, 'CRIATURA', 'QUALQUER');
INSERT INTO cartas (nome, descricao, ataque, defesa, mana, tipo, classe) VALUES ('Dragao Anciao', 'Criatura poderosa para fim de jogo', 8, 8, 10, 'CRIATURA', 'QUALQUER');
