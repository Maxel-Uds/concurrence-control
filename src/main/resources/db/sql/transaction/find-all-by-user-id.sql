SELECT
    id,
    user_id,
    tipo,
    valor,
    descricao,
    criado_em
FROM transactions
WHERE user_id = :userId
ORDER BY criado_em DESC LIMIT 10