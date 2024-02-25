UPDATE users SET
    saldo = saldo + :transactionValue
WHERE id = :userId
RETURNING saldo;