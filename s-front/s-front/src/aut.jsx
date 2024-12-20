// authorization.jsx
import React, { useState } from 'react';
import './aut.css'; // Подключаем CSS
import { Link, useNavigate } from 'react-router-dom'; // Импортируем useNavigate

function AutPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null); // Состояние для хранения ошибок

    const navigate = useNavigate(); // Получаем функцию для перехода

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        try {
            const response = await fetch('http://localhost:8080/api/users', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            // Проверяем статус ответа
            if (!response.ok) {
                throw new Error('Ошибка при получении пользователей');
            }

            const users = await response.json();

            // Проверяем, существует ли пользователь с введенными данными
            const user = users.find(u => u.username === username && u.password === password);

            if (user) {
                // Если авторизация успешна, передаем id пользователя в navigate
                navigate(`/start/${user.user_id}`); // Передаем id пользователя в URL
            } else {
                setError('Неверный логин или пароль'); // Устанавливаем сообщение об ошибке
            }
        } catch (error) {
            console.error('Ошибка при авторизации:', error);
            setError('Ошибка при авторизации. Пожалуйста, попробуйте еще раз.');
        }
    };

    return (
        <div className="auth-body"> {/* Добавили класс к body */}
            <div className="auth-container">
                <h1 className="custom-header">Авторизация</h1> {/* Добавили класс к заголовку */}
                <form onSubmit={handleSubmit} className="auth-form">
                    {error && <div className="auth-error">{error}</div>} {/* Отображаем ошибку, если она есть */}
                    <div className="auth-form-group"> {/* Переименовали класс */}
                        <label htmlFor="login" className="auth-form-group label">Логин</label>
                        <input 
                            type="text" // Тип остается текстом
                            id="login" 
                            value={username} 
                            onChange={(e) => setUsername(e.target.value)} 
                            required 
                            className="auth-form-group input" 
                        />
                    </div>
                    <div className="auth-form-group"> 
                        <label htmlFor="password" className="auth-form-group label">Пароль</label>
                        <input 
                            type="password" 
                            id="password" 
                            value={password} 
                            onChange={(e) => setPassword(e.target.value)} 
                            required 
                            className="auth-form-group input" 
                        />
                    </div>
                    <button type="submit" className="auth-button">Войти</button>
                    <div className="auth-footer">
                        <p>Нет аккаунта? <Link to="/register">Зарегистрироваться</Link></p> {/* Изменен на Link */}
                    </div>
                </form>
            </div>
        </div>
    );
}

export default AutPage;