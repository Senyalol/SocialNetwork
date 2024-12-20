import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import "./messanger.css";

function UsersIn() {
    const { id } = useParams(); // Получаем User ID
    const [searchTerm, setSearchTerm] = useState("");
    const [users, setUsers] = useState([]);
    const [friends, setFriends] = useState([]); // Состояние для хранения друзей
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [newChatName, setNewChatName] = useState("");

    // Fetch users from API
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/users");
                const data = await response.json();
                
                // Map API data to user structure
                const usersList = data.map(user => ({
                    id: user.user_id, // Assuming user_id is unique
                    name: `${user.firstName} ${user.lastName}`,
                    avatar: user.imageu, // User's avatar from the API
                }));
                
                setUsers(usersList);
            } catch (error) {
                console.error("Error fetching users:", error);
            }
        };

        const fetchFriends = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/friendships/${id}`); // Подразумевается, что API поддерживает получение друзей по user_id
                const data = await response.json();
                
                // Map API data to friends structure
                const friendsList = data.map(friend => friend.friend_id);
                setFriends(friendsList);
            } catch (error) {
                console.error("Error fetching friends:", error);
            }
        };

        fetchUsers();
        fetchFriends();
    }, [id]);

    // Filter out the current user from the list
    const filteredUsers = users.filter(user =>
        user.id !== parseInt(id) && 
        user.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const handleAddChat = () => {
        if (newChatName.trim() !== "") {
            const newChat = {
                id: users.length + 1,
                name: newChatName,
                avatar: `https://via.placeholder.com/50?text=${newChatName.charAt(0).toUpperCase()}`,
            };
            setUsers([...users, newChat]);
            setNewChatName("");
            setIsModalOpen(false); // Close modal after adding chat
        }
    };

    const handleAddFriend = async (friendId) => {
        if (friends.includes(friendId)) {
            alert("Этот пользователь уже в ваших друзьях.");
            return;
        }

        const friendshipData = {
            friendship_id: 1, // You may need to generate a unique ID for each friendship
            user_id: parseInt(id), // Current user ID from URL
            friend_id: friendId, // ID of the user to add as a friend
            status: "accepted"
        };

        try {
            const response = await fetch("http://localhost:8080/api/friendships", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(friendshipData),
            });

            if (response.ok) {
                const result = await response.json();
                console.log("Friend added successfully:", result);
                // Обновляем список друзей после успешного добавления
                setFriends([...friends, friendId]);
            } else {
                console.error("Error adding friend:", response.statusText);
            }
        } catch (error) {
            console.error("Error adding friend:", error);
        }
    };

    return (
        <div className="messages-page">
            <div className="navigation-buttons">
                <Link to={`/start/${id}`} className="back-button">Назад</Link>
                <button className="add-button" onClick={() => setIsModalOpen(true)}>Добавить чат</button>
            </div>
            <input
                type="text"
                placeholder="Искать пользователей..."
                className="search-input"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <div className="chat-list">
                {filteredUsers.map((user) => (
                    <div key={user.id} className="chat-card">
                        <img src={user.avatar} alt={`${user.name} avatar`} className="chat-avatar" />
                        <div className="chat-info">
                            <div className="chat-name">{user.name}</div>
                            <div className="chat-buttons">
                                <Link to={`/LookProf/${user.id}/${id}`} className="profile-button">
                                    <button>Посмотреть профиль</button>
                                </Link>
                                <button className="add-friend-button" onClick={() => handleAddFriend(user.id)}>Добавить в друзья</button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {/* Modal for Adding Chat */}
            {isModalOpen && (
                <div className="modal-overlay">
                    <div className="modal">
                        <h2>Добавить новый чат</h2>
                        <input
                            type="text"
                            placeholder="Введите имя друга"
                            value={newChatName}
                            onChange={(e) => setNewChatName(e.target.value)}
                            className="modal-input"
                        />
                        <button className="modal-button" onClick={handleAddChat}>Добавить</button>
                        <button className="modal-button" onClick={() => setIsModalOpen(false)}>Отменить</button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default UsersIn;