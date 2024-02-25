UPDATE users SET
    saldo  = saldo + :saldo
WHERE id = :userId;