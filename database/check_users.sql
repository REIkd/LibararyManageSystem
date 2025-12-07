-- 检查用户表中的密码哈希
USE library_management;

SELECT 
    user_id,
    username, 
    email,
    LEFT(password_hash, 50) as password_hash_preview,
    role,
    status
FROM users 
WHERE username IN ('admin', 'instructor1', 'user1');

-- 显示完整的密码哈希
SELECT username, password_hash FROM users WHERE username = 'admin';

