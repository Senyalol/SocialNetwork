import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Импортируем useNavigate
import './aut.css'; 

const RegPage = () => {
    const navigate = useNavigate(); // Получаем функцию для навигации
    const [username, setUsername] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleRegistration = async (event) => {
        event.preventDefault(); // Предотвращаем перезагрузку страницы
    
        const userData = {
            username,
            password,
            email,
            firstName,
            lastName,
            imageu: "" // Устанавливаем изображение по умолчанию как пустую строку
        };
    
        try {
            const response = await fetch('http://localhost:8080/api/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            });
    
            // Проверка статуса ответа
            if (!response.ok) {
                const errorText = await response.text(); // Читаем текст ответа
                throw new Error(`Ошибка при регистрации: ${response.status} ${errorText}`);
            }
    
            // Проверка на наличие ответа
            const textResponse = await response.text();
            if (textResponse) {
                try {
                    const data = JSON.parse(textResponse); // Пробуем разобрать JSON
                    console.log('Успешная регистрация:', data);
                    navigate('/'); // Перенаправление после успешной регистрации
                    alert("Аккаунт успешно создан! ");
                } catch (jsonError) {
                    throw new Error('Ошибка при разборе ответа сервера: ' + jsonError.message);
                }
            } else {
                throw new Error('Ответ сервера пустой');
            }
        } catch (error) {
            console.error('Ошибка:', error);
            alert(error.message); // Пример простого оповещения об ошибке
        }
    };

    const handleLoginRedirect = () => {
        navigate('/'); // Путь к странице авторизации
    };

    return (
        <div className="auth-body">
            <div className="auth-container">
                <h2 className="custom-header">Регистрация</h2>
                <form onSubmit={handleRegistration}>
                    <div className="auth-form-group">
                        <label htmlFor="username">Логин</label>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="auth-form-group">
                        <label htmlFor="firstName">Имя</label>
                        <input
                            type="text"
                            id="firstName"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="auth-form-group">
                        <label htmlFor="lastName">Фамилия</label>
                        <input
                            type="text"
                            id="lastName"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="auth-form-group">
                        <label htmlFor="email">Почта</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="auth-form-group">
                        <label htmlFor="password">Пароль</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="auth-button">Зарегистрироваться</button>
                </form>
                <div className="auth-footer">
                    <p>Уже есть аккаунт? <a onClick={handleLoginRedirect} style={{ cursor: 'pointer', color: 'blue' }}>Войти</a></p>
                </div>
            </div>
        </div>
    );
};

export default RegPage;