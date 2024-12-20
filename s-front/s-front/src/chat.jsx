import React, { useState, useEffect, useRef } from "react";
import { Link, useParams } from "react-router-dom";
import "./chat.css";

function ChatPage() {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const inputRef = useRef(null);
    const { id, friendId } = useParams();

    const taboowords = ['Воронец','Блин','Черт'];

    // Функция для фильтрации текста и замены запрещенных слов на ***
    const filterMessage = (message) => {
        let filteredMessage = message;
        taboowords.forEach(word => {
            const regex = new RegExp(`\\b${word}\\b`, 'gi'); // Регистронезависимый поиск слова
            filteredMessage = filteredMessage.replace(regex, '*'.repeat(word.length));
        });
        return filteredMessage;
    };

    useEffect(() => {
        if (!id || !friendId) {
            console.error("ID или friendId неопределены");
            return;
        }

        const fetchMessages = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/messages/between/${id}/${friendId}`);
                if (!response.ok) {
                    throw new Error("Ошибка при получении сообщений");
                }
                const data = await response.json();

                const formattedMessages = data.map(message => ({
                    id: message.message_id,
                    text: filterMessage(message.user_content), // Применяем фильтрацию здесь
                    sender: message.sender_id === parseInt(id) ? "me" : "friend",
                }));

                setMessages(formattedMessages);
            } catch (error) {
                console.error("Ошибка при получении сообщений:", error);
            }
        };

        fetchMessages();
    }, [id, friendId]);

    const handleSendMessage = async () => {
        if (input.trim()) {
            // Создаем новое сообщение
            let filteredInput = input.trim();

            // Проверяем наличие запрещенных слов и заменяем их на ***
            taboowords.forEach(word => {
                const regex = new RegExp(`\\b${word}\\b`, 'gi');
                filteredInput = filteredInput.replace(regex, '***');
            });

            console.log("Отправляемое сообщение:", filteredInput); // Для отладки

            const newMessage = {
                sender_id: parseInt(id),
                receiver_id: parseInt(friendId),
                user_content: filteredInput, // Используем отфильтрованное сообщение
            };

            try {
                const response = await fetch("http://localhost:8080/api/messages", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(newMessage),
                });

                if (!response.ok) {
                    throw new Error("Ошибка при отправке сообщения");
                }

                setMessages(prevMessages => [
                    ...prevMessages,
                    { ...newMessage, sender: "me", id: prevMessages.length + 1 }
                ]);
                setInput("");
                inputRef.current.focus();
            } catch (error) {
                console.error("Ошибка при отправке сообщения:", error);
            }
        }
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter' && !event.shiftKey) {
            event.preventDefault();
            handleSendMessage();
        }
    };

    return (
        <div className="chat-wrapper">
            <div className="chat-header">
                <img src="path/to/avatar.jpg" alt="User Avatar" className="user-avatar" />
                <span className="user-name">Имя пользователя</span>
                <Link to={`/friends/${id}`} className="link-to-messages">К сообщениям</Link>
            </div>
            <div className="message-list">
                {messages.length > 0 ? messages.map((message) => (
                    <div key={message.id} className={`chat-message ${message.sender === 'friend' ? 'message-from-friend' : 'message-from-me'}`}>
                        {message.text}
                    </div>
                )) : (
                    <div className="no-messages">Нет сообщений</div>
                )}
            </div>
            <div className="input-wrapper">
                <input
                    type="text"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    placeholder="Введите сообщение..."
                    onKeyPress={handleKeyPress}
                    ref={inputRef}
                    autoFocus
                />
                <button onClick={handleSendMessage}>Отправить</button>
            </div>
        </div>
    );
}

export default ChatPage;