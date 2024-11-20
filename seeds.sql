







-- Inserir roles com id e created_date especificados
INSERT INTO roles (id, created_date, name) VALUES
('bd2a76d3-484e-4135-b856-130b1b2092fb', CURRENT_TIMESTAMP, 'ROLE_ADMIN'),
('3df4659e-a5f8-43ff-a510-8ae819589c6c', CURRENT_TIMESTAMP, 'ROLE_USER');

-- Inserir usuários
INSERT INTO users (id, created_date, updated_date, name, phone_number, email, password) VALUES
('b16e7fe7-6c2f-4416-ae41-3ba3c2aea2a9',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,  'Leila Silva', '+55 11 91234-5678', 'leila@admin.com', '$2a$10$rvJHeTJbraZ7FKeWLF3MquAGLJ3hSovrtXxhRviaHNVmsNa4tPA3y'),
('6c39600a-6139-4cbb-9809-9efd8b1b08f8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'Bruno Dias', '+55 11 98765-4321', 'bruno@teste.com', '$2a$10$rvJHeTJbraZ7FKeWLF3MquAGLJ3hSovrtXxhRviaHNVmsNa4tPA3y');


-- Associar ROLE_ADMIN ao usuário 'Leila Silva'
INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE email = 'leila@admin.com'),
    (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')
);

-- Associar ROLE_USER ao usuário 'Bruno Dias'
INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE email = 'bruno@teste.com'),
    (SELECT id FROM roles WHERE name = 'ROLE_USER')
);


-- Inserir serviços na tabela `services`
INSERT INTO services (id, created_date, name, description, price) VALUES
(gen_random_uuid(), CURRENT_TIMESTAMP, 'Corte de Cabelo Feminino', 'Corte de cabelo Feminino moderno e estiloso.', 70.0),
(gen_random_uuid(), CURRENT_TIMESTAMP, 'Corte de Cabelo Masculino', 'Corte de cabelo masculino moderno e estiloso.', 50.0),
(gen_random_uuid(), CURRENT_TIMESTAMP, 'Manicure', 'Manicure completa com esmaltação de qualidade.', 30.0),
(gen_random_uuid(), CURRENT_TIMESTAMP, 'Pedicure', 'Pedicure relaxante com cuidados completos.', 35.0),
(gen_random_uuid(), CURRENT_TIMESTAMP, 'Escova Modeladora', 'Modelagem capilar com escova para um visual sofisticado.', 50.0),
(gen_random_uuid(), CURRENT_TIMESTAMP, 'Hidratação Capilar', 'Tratamento de hidratação profunda para cabelos danificados.', 90.0),
(gen_random_uuid(), CURRENT_TIMESTAMP, 'Depilação', 'Depilação corporal completa.', 80.0),
(gen_random_uuid(), CURRENT_TIMESTAMP, 'Design de Sobrancelhas', 'Design de sobrancelhas com pinça e modelagem.', 25.0),
(gen_random_uuid(), CURRENT_TIMESTAMP, 'Maquiagem', 'Maquiagem profissional para eventos.', 120.0);

