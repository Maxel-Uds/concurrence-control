SELECT
--    pg_advisory_xact_lock(:userId),
    id,
    saldo,
    limite
FROM users WHERE id = :userId;